package com.mayvel.snDriver.component;

import com.mayvel.snDriver.utils.Generic;
import com.mayvel.snDriver.utils.Logger;
import com.tridium.alarm.BConsoleRecipient;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.history.*;
import javax.baja.history.db.BHistoryDatabase;
import javax.baja.history.db.HistoryDatabaseConnection;
import javax.baja.naming.BOrd;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Cursor;
import javax.baja.sys.Sys;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;


public class HistoryDBHelper {
    private static Map<String, BiConsumer<BConsoleRecipient, BAlarmRecord>> listeners = new HashMap<>();

    public static String alarmClassData;
    public static String timeStampData;
    public static String normalTimeStampData;
    public static String uUidData;

    public static Map<String, String> convertToSyncallMap(BHistoryRecord record, String filterValues) {
        Map<String, String> mapData = new HashMap<>();

        // Convert comma-separated filterValues to a Set
        Set<String> filterKeys = new HashSet<>();
        if (filterValues != null && !filterValues.trim().isEmpty()) {
            for (String key : filterValues.split(",")) {
                filterKeys.add(key.trim());
            }
        }

        // Parsing alarm data
        BHistorySchema schema = record.getSchema();
        String schemaStr = schema.toString();
        // Split the schema to get field names
        String[] fields = schemaStr.split(";");
        for (String field : fields) {
            String[] keyValue = field.split(",", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String type = keyValue[1].trim();

                // Filter logic
                if (!filterKeys.isEmpty() && !filterKeys.contains(key)) {
                    continue; // skip fields not in the filter
                }

                try {
                    BValue value = record.get(key);
                    if (value != null) {
                        mapData.put(key, value.toString());
                        Logger.Log("10 Field: " + key + " => Type: " + type + " => Value: " + value.toString());
                    } else {
                        mapData.put(key, "null");
                        Logger.Log("10 Field: " + key + " => Type: " + type + " => Value: null");
                    }
                } catch (Exception e) {
                    Logger.Log("Error reading value for field '" + key + "': " + e.getMessage());
                }
            } else {
                Logger.Log("Malformed schema field: " + field);
            }
        }
        return mapData;
    }

    public static Map<String, String> convertToMap(BAlarmRecord alarm) {
        Map<String, String> mapData = new HashMap<>();

        // Parsing alarm data
        String alarmDataString = alarm.getAlarmData().toString();
        Map<String, String> alarmDataMap = parseData(alarmDataString);
        mapData.putAll(alarmDataMap);
        return mapData;
    }

    public static Map<String, String> parseData(String data) {
        String[] pairs = data.split(",");
        Map<String, String> map = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    public static Map<String, Object> GetAllHistory(String StartTime, String EndTime, String limit, String offset, String historySource, String filterValues, boolean firstAndLastOnly) {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, List<Map<String, String>>> historyRecordsMap = new LinkedHashMap<>();
        int totalRecords = 0;
        int totalScanned = 0;

        try {
            BHistoryService historyService = (BHistoryService) Sys.getService(BHistoryService.TYPE);
            if (historyService == null) {
                Logger.Error("History service not available");
                responseMap.put("error", "History service not available");
                return responseMap;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy hh:mm:ss a");
            LocalDateTime startDateTime = LocalDateTime.parse(StartTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(EndTime, formatter);

            String[] historyNames = historySource.split(",");
            for (String historyName : historyNames) {
                historyName = historyName.trim();
                String path = getHistoryPathByName(historyName);
                if (path == null) {
                    Logger.Log("History not found for: " + historyName);
                    continue;
                }

                BOrd ord = BOrd.make(path);
                BIHistory history = (BIHistory) ord.resolve().get();
                BHistoryConfig config = history.getConfig();
                BHistoryDatabase db = historyService.getDatabase();
                db.setConfig(config);
                BHistoryId historyId = config.getId();
                db.getSystemTable(String.valueOf(historyId));

                BIHistory[] tablesRecord = db.getHistories();
                for (BIHistory hist : tablesRecord) {
                    if (hist.getId().toString().equals(extractPath(path))) {
                        HistoryDatabaseConnection conn = db.getDbConnection(null);
                        Cursor<BHistoryRecord> cursor = conn.scan(hist);

                        List<Map<String, String>> resultList = new ArrayList<>();
                        List<Map<String, String>> tempFiltered = new ArrayList<>();

                        while (cursor.next()) {
                            try {
                                totalScanned++;
                                BHistoryRecord record = cursor.get();
                                String formattedTime = Generic.formatTimeStamp(record.getTimestamp());
                                LocalDateTime recordTime = LocalDateTime.parse(formattedTime, formatter);

                                if ((recordTime.isAfter(startDateTime) || recordTime.equals(startDateTime)) &&
                                        (recordTime.isBefore(endDateTime) || recordTime.equals(endDateTime))) {

                                    totalRecords++;
                                    Map<String, String> recordMap = convertToSyncallMap(record, filterValues);
                                    if (firstAndLastOnly) {
                                        tempFiltered.add(recordMap);
                                    } else {
                                        resultList.add(recordMap);
                                    }
                                }
                            } catch (Exception e) {
                                Logger.Error("Record parse error: " + e.getMessage());
                            }
                        }

                        if (firstAndLastOnly && !tempFiltered.isEmpty()) {
                            resultList.add(tempFiltered.get(0));
                            if (tempFiltered.size() > 1) {
                                resultList.add(tempFiltered.get(tempFiltered.size() - 1));
                            }
                        }

                        conn.close();
                        historyRecordsMap.put(historyName, resultList);
                    }
                }
            }
        } catch (Exception e) {
            Logger.Error("Error in GetAllHistory: " + e.getMessage());
            responseMap.put("error", e.getMessage());
            return responseMap;
        }

        responseMap.put("filteredRecordCount", totalRecords);
        responseMap.put("totalDatabaseRecordCount", totalScanned);
        responseMap.put("filterValues", filterValues);
        responseMap.put("histories", historyRecordsMap);
        return responseMap;
    }

    private static String extractPath(String input) {
        if (input == null || !input.contains(":")) {
            return input; // or return null;
        }

        // Split the string by colon
        String[] parts = input.split(":", 2);
        return parts[1]; // This will be "/NetCool/TestPoint01"
    }

    public static Map<String, Object> GetAllHistoryFromDB(String StartTime, String EndTime, String limit, String offset, String historySource, String filterValues, boolean firstAndLastOnly) {
        Map<String, Object> responseMap = new HashMap<>();
        Map<String, List<Map<String, String>>> historyData = new LinkedHashMap<>();
        int totalFiltered = 0;
        int totalScanned = 0;

        try {
            BHistoryService historyService = (BHistoryService) Sys.getService(BHistoryService.TYPE);
            if (historyService == null) {
                responseMap.put("error", "History service not available");
                return responseMap;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy hh:mm:ss a");
            LocalDateTime endDateTime = LocalDateTime.parse(EndTime, formatter);

            String[] sourceNames = historySource.split(",");
            for (String name : sourceNames) {
                name = name.trim();
                String path = getHistoryPathByName(name);
                if (path == null) {
                    Logger.Log("❌ History not found: " + name);
                    continue;
                }

                BOrd ord = BOrd.make("history:" + path);  // Ensure prefix `history:` is added
                BIHistory history = (BIHistory) ord.resolve().get();
                BHistoryConfig config = history.getConfig();

                HistoryDatabaseConnection conn = historyService.getDatabase().getDbConnection(null);
                Cursor<BHistoryRecord> cursor = conn.scan(history);
                List<Map<String, String>> records = new ArrayList<>();
                List<Map<String, String>> tempFiltered = new ArrayList<>();

                while (cursor.next()) {
                    try {
                        totalScanned++;
                        BHistoryRecord record = cursor.get();
                        String formattedTimeStamp = Generic.formatTimeStamp(record.getTimestamp());
                        LocalDateTime recordTime = LocalDateTime.parse(formattedTimeStamp, formatter);

                        if ((recordTime.isBefore(endDateTime) || recordTime.equals(endDateTime))) {
                            totalFiltered++;
                            Map<String, String> recordMap = convertToSyncallMap(record, filterValues);
                            if (firstAndLastOnly) {
                                tempFiltered.add(recordMap);
                            } else {
                                records.add(recordMap);
                            }
                        }
                    } catch (Exception e) {
                        Logger.Error("Error reading record: " + e.getMessage());
                    }
                }

                if (firstAndLastOnly && !tempFiltered.isEmpty()) {
                    records.add(tempFiltered.get(0));
                    if (tempFiltered.size() > 1) {
                        records.add(tempFiltered.get(tempFiltered.size() - 1));
                    }
                }

                conn.close();
                historyData.put(name, records);
            }

        } catch (Exception e) {
            responseMap.put("error", e.getMessage());
            return responseMap;
        }

        responseMap.put("filterValues", filterValues);
        responseMap.put("totalDatabaseRecordCount", totalScanned);
        responseMap.put("filteredRecordCount", totalFiltered);
        responseMap.put("histories", historyData);
        return responseMap;
    }



    public static String[] getAllHistoryCount() {
        try {
            BHistoryService historyService = (BHistoryService) Sys.getService(BHistoryService.TYPE);
            if (historyService == null) {
                Logger.Error("❌ BHistoryService not available.");
                return new String[0];
            }

            BHistoryDatabase db = historyService.getDatabase();
            if (db == null) {
                Logger.Error("❌ BHistoryDatabase not available.");
                return new String[0];
            }

            BIHistory[] histories = db.getHistories();
            if (histories == null || histories.length == 0) {
                Logger.Log("ℹ️ No histories found in the station.");
                return new String[0];
            }

            String[] historyNames = new String[histories.length];

            for (int i = 0; i < histories.length; i++) {
                try {
                    BHistoryConfig config = histories[i].getConfig();
                    historyNames[i] = config.getId().toString(); // Store name in array
                } catch (Exception e) {
                    Logger.Log("⚠️ Error reading a history config: " + e.getMessage());
                    historyNames[i] = "error";
                }
            }

            return historyNames;

        } catch (Exception e) {
            Logger.Error("❌ Error fetching history names: " + e.getMessage());
            return new String[0];
        }
    }

    public static String getHistoryPathByName(String name) {
        String[] allPaths = getAllHistoryCount(); // This returns paths like "/NetCool/..."

        for (String path : allPaths) {
            if (path != null && path.endsWith("/" + name)) {
                return "history:"+path;
            }
        }
        return null; // or throw an exception if preferred
    }
}
