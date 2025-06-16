package com.mayvel.snDriver.utils;

import com.mayvel.snDriver.BSnDriverNetwork;
import com.tridium.json.JSONObject;

import javax.baja.naming.BOrd;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import java.io.*;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributeView;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomLicenseGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String LICENSE_FOLDER_NAME = "._licStore";
    private static final String LICENSE_FILE_NAME = "licenseDetails.dat";

    public static String getRandom8() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public static String getRandom7() {
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    // Generate license key
    public static String generateCustomLicense(String mac, String validFrom, String validThru) {
        String cleanMac = mac.replace("-", "").toUpperCase();
        String rawData = cleanMac + validFrom.replace("-", "") + validThru.replace("-", "");
        StringBuilder licenseBuilder = new StringBuilder();
        licenseBuilder.append(getRandom8());

        for (char ch : rawData.toCharArray()) {
            licenseBuilder.append(ch);
            licenseBuilder.append(getRandom8());
        }

        return licenseBuilder.toString();
    }


    public static String decryptCustomLicense(String license) {
        if (license == null || license.length() < 252) {
            return "{\"result\":false,\"message\":\"Invalid license length.\"}";
        }

        try {
            String[] parts = license.split("7:~", 2);
            String mainPart = parts[0];
            String encodedPlan = parts.length > 1 ? parts[1] : null;

            StringBuilder original = new StringBuilder();
            int index = 8;

            while (index < mainPart.length()) {
                original.append(mainPart.charAt(index));  // Extract original char
                index += 9;
            }

            String combined = original.toString();
            if (combined.length() != 28) {
                return "{\"result\":false,\"message\":\"Corrupted license data.\"}";
            }

            String macRaw = combined.substring(0, 12);
            String fromDateRaw = combined.substring(12, 20);
            String toDateRaw = combined.substring(20, 28);

            StringBuilder formattedMac = new StringBuilder();
            for (int i = 0; i < macRaw.length(); i += 2) {
                if (i > 0) formattedMac.append("-");
                formattedMac.append(macRaw.substring(i, i + 2));
            }

            String validFrom = fromDateRaw.substring(0, 4) + "-" + fromDateRaw.substring(4, 6) + "-" + fromDateRaw.substring(6, 8);
            String validThru = toDateRaw.substring(0, 4) + "-" + toDateRaw.substring(4, 6) + "-" + toDateRaw.substring(6, 8);

            JSONObject json = new JSONObject();
            json.put("result", true);
            json.put("mac", formattedMac.toString());
            json.put("validFrom", validFrom);
            json.put("validThru", validThru);
            json.put("message", "License decoded successfully.");

            if (encodedPlan != null && !encodedPlan.isEmpty()) {
                try {
                    String decodedPlanJson = new String(Base64.getDecoder().decode(encodedPlan));
                    JSONObject plan = new JSONObject(decodedPlanJson);
                    // 🟢 Iterate and merge each key from plan to the main json
                    for (String key : plan.keySet()) {
                        json.put(key, plan.get(key));
                    }
                } catch (Exception e) {
                    json.put("planError", "Failed to decode plan data: " + e.getMessage());
                }
            }

            return json.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"" + e.toString().replace("\"", "\\\"") + "\"}";
        }
    }

    // Gets the MAC address of the first network interface
    public static String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface network = networkInterfaces.nextElement();
            byte[] mac = network.getHardwareAddress();

            if (mac != null && mac.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                return sb.toString();  // Return the first MAC address found
            }
        }
        return null;  // No MAC address found
    }

    public static String getMacAddressSafe() {
        try {
            return getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        public static String isLicenseValid(String license, String macAddress) {
            if (license == null || license.isEmpty()) {
                return "{\"result\":false,\"message\":\"License cannot be empty!\"}";
            }
            if (macAddress == null || macAddress.isEmpty()) {
                return "{\"result\":false,\"message\":\"MAC Address cannot be empty!\"}";
            }

            try {
                String jsonString = decryptCustomLicense(license);
                JSONObject json = new JSONObject(jsonString);

                boolean res = json.getBoolean("result");

                if (res) {
                    String extractedMac = json.getString("mac");
                    String validFrom = json.getString("validFrom");
                    String validThru = json.getString("validThru");
                    String planName = json.getString("planName");
                    int SnHttpClient = json.getInt("SnHttpClient");
                    int SnScheduler = json.getInt("SnScheduler");
                    int SnAlarm = json.getInt("SnAlarm");
                    int SnHistory = json.getInt("SnHistory");
                    JSONObject resultJson = new JSONObject();

                    resultJson.put("validFrom", validFrom);
                    resultJson.put("validThru", validThru);
                    resultJson.put("planName", planName);
                    resultJson.put("SnHttpClient", SnHttpClient);
                    resultJson.put("SnScheduler", SnScheduler);
                    resultJson.put("SnAlarm", SnAlarm);
                    resultJson.put("SnHistory", SnHistory);

                    if (extractedMac != null && extractedMac.equalsIgnoreCase(macAddress)) {
                        // Check if the license is expired
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sdf.setLenient(false);

                        Date to = sdf.parse(validThru);
                        Date today = sdf.parse(sdf.format(new Date())); // Removes time part

                        if (!to.after(today)) {
                            resultJson.put("result", false);
                            resultJson.put("message", "License is expired.");
                        } else {
                            resultJson.put("result", true);
                            resultJson.put("message", "License is valid!");
                        }
                        return resultJson.toString();

                    } else {
                        return "{\"result\":false,\"message\":\"License is invalid for this MAC address.\"}";
                    }

                } else {
                    String errorMessage = json.getString("message");
                    return "{\"result\":false,\"message\":\"" + errorMessage.replace("\"", "\\\"") + "\"}";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "{\"result\":false,\"message\":\"" + e.toString().replace("\"", "\\\"") + "\"}";
            }
        }


    public static String validateLicense() {
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getLicenseKey();
            }
            String macAddress = getMacAddressSafe();
            return isLicenseValid(licenseKey, macAddress);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static String getLicenseKey(){
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getLicenseKey();
            }
            return licenseKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static String getSnHttpClientLimit(){
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getSnHttpClientLimit();
            }
            return licenseKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static String getSnSchedulerLimit(){
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getSnSchedulerLimit();
            }
            return licenseKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static String getSnAlarmLimit(){
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getSnAlarmLimit();
            }
            return licenseKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static String getSnHistoryLimit(){
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            String path="";
            if(paths.length>0){
                path=paths[0];
            }
            String licenseKey = "";
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();
            if (comp instanceof BSnDriverNetwork) {
                licenseKey = ((BSnDriverNetwork) comp).getSnHistoryLimit();
            }
            return licenseKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"result\":false,\"message\":\"SnDriverNetwork cannot resolve\"}";
        }
    }

    public static boolean isSnLimitValid(String type) {
        try {
            String[] paths = getAllSnDriverNetworkPaths();
            if (paths.length == 0) {
                return false;
            }

            String path = paths[0];
            BOrd ord = BOrd.make(path);
            BComponent comp = (BComponent) ord.resolve().get();

            if (!(comp instanceof BSnDriverNetwork)) {
                return false;
            }

            String limitValue = "";

            switch (type.toLowerCase()) {
                case "http":
                    limitValue = ((BSnDriverNetwork) comp).getSnHttpClientLimit();
                    break;
                case "scheduler":
                    limitValue = ((BSnDriverNetwork) comp).getSnSchedulerLimit();
                    break;
                case "alarm":
                    limitValue = ((BSnDriverNetwork) comp).getSnAlarmLimit();
                    break;
                case "history":
                    limitValue = ((BSnDriverNetwork) comp).getSnHistoryLimit();
                    break;
                default:
                    return false;
            }

            String[] parts = limitValue.split("/");
            if (parts.length == 2) {
                int used = Integer.parseInt(parts[0].trim());
                int total = Integer.parseInt(parts[1].trim());
                return used < total;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Helper method to get/create the hidden license folder inside station home.
     */
    private static File getLicenseFolder() throws Exception {
        File stationHome = Sys.getProtectedStationHome();
        if (stationHome == null || !stationHome.exists()) {
            throw new Exception("Sys.getProtectedStationHome() returned null or non-existent path");
        }

        File folder = new File(stationHome, LICENSE_FOLDER_NAME);

        // If folder or license file already exists, delete them
        File licenseFile = new File(folder, LICENSE_FILE_NAME);
        if (licenseFile.exists()) {
            licenseFile.delete();
        }

        if (folder.exists()) {
            folder.delete();
        }

        // Recreate the folder
        folder.mkdirs();

        // Try to set hidden attribute (Windows only)
        try {
            DosFileAttributeView attr = Files.getFileAttributeView(folder.toPath(), DosFileAttributeView.class);
            if (attr != null) {
                attr.setHidden(true);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optional warning
        }

        return folder;
    }


    public static int getHttpRequestCount() {
        try {
            JSONObject json = readJsonFromFile();
            if (json != null) {
                return json.optInt("apiRequestCount", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAlarmReceivedCount() {
        try {
            JSONObject json = readJsonFromFile();
            if (json != null) {
                return json.optInt("alarmReceivedCount", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAllScheduledCount() {
        try {
            JSONObject json = readJsonFromFile();
            if (json != null) {
                return json.optInt("scheduledCount", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAllHistoryApiRequestCount() {
        try {
            JSONObject json = readJsonFromFile();
            if (json != null) {
                return json.optInt("historyApiRequestCount", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static JSONObject readJsonFromFile() {
        try {
            String content = readLSFile(); // Your existing method
            if (content == null) return null;
            return new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeJsonToFile(JSONObject json) {
        try {
            File folder = getLicenseFolder();
            File file = new File(folder, LICENSE_FILE_NAME);

            String content = json.toString();
            String encoded = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));

            String randomString = CustomLicenseGenerator.getRandom7() + encoded + CustomLicenseGenerator.getRandom7();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(randomString);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String createLicenseDetailsJsonString() {
        JSONObject json = new JSONObject();
        json.put("licenseKey", CustomLicenseGenerator.getLicenseKey());
        json.put("apiRequestCount", CustomLicenseGenerator.getHttpRequestCount());
        json.put("alarmReceivedCount", CustomLicenseGenerator.getAlarmReceivedCount());
        json.put("scheduledCount",CustomLicenseGenerator.getAllScheduledCount());
        json.put("historyApiRequestCount", CustomLicenseGenerator.getAllHistoryApiRequestCount());

        return json.toString(); // converts to JSON string
    }

    /**
     * Writes base64-encoded "hello world" to licenseDetails.dat inside hidden license folder.
     */
    public static String writeLSFile() {
        try {
            File folder = CustomLicenseGenerator.getLicenseFolder();
            File file = new File(folder, LICENSE_FILE_NAME);

            String content =CustomLicenseGenerator.createLicenseDetailsJsonString();
            String encoded = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
            String randomString = CustomLicenseGenerator.getRandom7() + encoded+CustomLicenseGenerator.getRandom7();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(randomString);
            }

            return "Write successful: " + file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Write failed: " + e.getMessage();
        }
    }

    /**
     * Reads and decodes base64 content from licenseDetails.dat inside hidden license folder.
     */
    public static String readLSFile() {
        try {
            File folder = getLicenseFolder();
            File file = new File(folder, LICENSE_FILE_NAME);

            if (!file.exists()) {
                String msg = "File does not exist: " + file.getAbsolutePath();
                return null;
            }

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            String fullContent = sb.toString();

            if (fullContent.length() <= 14) {
                throw new Exception("Invalid file content length (less than or equal to 14)");
            }

            // Remove first 7 and last 7 characters
            String base64Content = fullContent.substring(7, fullContent.length() - 7);

            // Decode base64 content
            byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
            String decodedContent = new String(decodedBytes, "UTF-8");
            return decodedContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String[] getAllSnDriverNetworkPaths() {
        try {
            // Start from the root of the station
            BOrd ord = BOrd.make("station:|slot:/");
            BComponent root = (BComponent) ord.resolve().get();

            // List to hold all matching paths
            List<String> paths = new ArrayList<>();

            // Recursively collect all paths
            collectSnDriverNetworkPaths(root, paths);

            return paths.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    private static void collectSnDriverNetworkPaths(BComponent parent, List<String> paths) {
        try {
            if (parent instanceof BSnDriverNetwork) {
                paths.add("station:|"+parent.getSlotPath().toString());
            }

            BComponent[] children = parent.getChildComponents();
            for (BComponent child : children) {
                collectSnDriverNetworkPaths(child, paths);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}