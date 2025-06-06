package com.mayvel.snDriver.utils;

import com.mayvel.snDriver.BSnDriverNetwork;
import com.tridium.json.JSONObject;

import javax.baja.naming.BOrd;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class CustomLicenseGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String getRandom8() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
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

    // Decrypt license key
    public static String decryptCustomLicense(String license) {
        if (license == null || license.length() < 252) {
            return "{\"result\":false,\"message\":\"Invalid license length.\"}";
        }

        try {
            StringBuilder original = new StringBuilder();
            int index = 8;  // Skip initial random block

            while (index < license.length()) {
                original.append(license.charAt(index));  // Extract original char
                index += 9;  // Skip the 8 random chars
            }

            String combined = original.toString();

            if (combined.length() != 28) {
                return "{\"result\":false,\"message\":\"Corrupted license data.\"}";
            }

            String macRaw = combined.substring(0, 12);
            String fromDateRaw = combined.substring(12, 20);  // "20250605"
            String toDateRaw = combined.substring(20, 28);    // "20250606"

            // Format MAC: XX-XX-XX-XX-XX-XX
            StringBuilder formattedMac = new StringBuilder();
            for (int i = 0; i < macRaw.length(); i += 2) {
                if (i > 0) formattedMac.append("-");
                formattedMac.append(macRaw.substring(i, i + 2));
            }

            // Format date: yyyyMMdd → yyyy-MM-dd
            String validFrom = fromDateRaw.substring(0, 4) + "-" + fromDateRaw.substring(4, 6) + "-" + fromDateRaw.substring(6, 8);
            String validThru = toDateRaw.substring(0, 4) + "-" + toDateRaw.substring(4, 6) + "-" + toDateRaw.substring(6, 8);

            // Build JSON response
            JSONObject json = new JSONObject();
            json.put("result", true);
            json.put("mac", formattedMac.toString());
            json.put("validFrom", validFrom);
            json.put("validThru", validThru);
            json.put("message", "License decoded successfully.");

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

                if (extractedMac != null && extractedMac.equalsIgnoreCase(macAddress)) {
                    // Validate that validThru is in the future (not today or past)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);

                    Date to = sdf.parse(validThru);
                    Date today = sdf.parse(sdf.format(new Date())); // strips time part

                    if (!to.after(today)) {
                        return "{\"result\":false,\"message\":\"License is expired.\",\"validFrom\":\"" + validFrom + "\",\"validThru\":\"" + validThru + "\"}";
                    }
                    return "{\"result\":true,\"message\":\"License is valid!\",\"validFrom\":\"" + validFrom + "\",\"validThru\":\"" + validThru + "\"}";
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