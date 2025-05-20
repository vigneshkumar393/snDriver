package com.mayvel.snDriver.utils;
import com.mayvel.snDriver.Const.Consts;
import com.mayvel.snDriver.component.AlarmDBHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.tridium.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpAlarmPost {
    private static HttpServer server;

    /** Starts the HTTP server */
    public static void start() throws IOException {
        if (server != null) {
            System.out.println("Server already started.");
            return;
        }

        server = HttpServer.create(new InetSocketAddress(Consts.alarmPort), 0);
        server.createContext(Consts.alarm_sync_all_route, new AlarmHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("HTTP Alarm Server started on port " + Consts.alarmPort);
    }

    /** Stops the HTTP server */
    public static void stop() {
        if (server != null) {
            server.stop(0);  // 0 seconds delay for immediate stop
            server = null;
            System.out.println("HTTP Alarm Server stopped.");
        }
    }

    static class AlarmHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange t)throws IOException{
            // Assuming you're sending JSON from Postman
            URI requestURI = t.getRequestURI();
            InputStream is = t.getRequestBody();
            String query = requestURI.getQuery();
            Map<String,String> queryParams = parseQuery(query);
            String limit = queryParams.get("limit");
            String offset = queryParams.get("offset");
            byte[] bytes = readFully(is);
            String requestBody = new String(bytes, StandardCharsets.UTF_8);
            JSONObject jsonBody = new JSONObject(requestBody);

            String StarTime = jsonBody.optString("startTime","");
            String EndTime = jsonBody.optString("endTime","");

            Map<String, Object> responseMap;
            if(StarTime==""){
                responseMap=  AlarmDBHelper.GetAllAlarmsFromDB(StarTime,EndTime,limit,offset);
            }else{
                responseMap = AlarmDBHelper.GetAllAlarms(StarTime,EndTime,limit,offset);
            }

            JSONObject jsonResponse = new JSONObject(responseMap);
            String response =jsonResponse.toString();
            t.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
            t.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }
    private static byte[] readFully(InputStream input) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        return output.toByteArray();
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return result;
        }

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
