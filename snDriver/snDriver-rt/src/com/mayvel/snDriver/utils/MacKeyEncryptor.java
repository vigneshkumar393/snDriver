package com.mayvel.snDriver.utils;
import com.mayvel.snDriver.Const.Consts;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Enumeration;

public class MacKeyEncryptor {

    // Generates a random 8-digit string
    private static String getRandom14() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(14);
        for (int i = 0; i < 14; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    // Removes hyphens from MAC and splits it into two 6-char parts
    public static String[] splitMac(String mac) {
        String cleanMac = mac.replace("-", "").toUpperCase();
        String macPart1 = cleanMac.substring(0, 6);
        String macPart2 = cleanMac.substring(6);
        return new String[]{macPart1, macPart2};
    }

    // Generates a custom formatted unique key
    public static String generateUniqueKey(String mac) {
        String cleanMac = mac.replace("-", "").toUpperCase(); // 12 characters
        if (cleanMac.length() != 12) {
            throw new IllegalArgumentException("Invalid MAC address format.");
        }

        StringBuilder keyBuilder = new StringBuilder();

        // Start with a random 14-character string
        keyBuilder.append(getRandom14());

        for (int i = 0; i < 12; i++) {
            // Append one MAC character
            keyBuilder.append(cleanMac.charAt(i));
            // Append a random 14-character string after each MAC character
            keyBuilder.append(getRandom14());
        }

        return keyBuilder.toString();
    }


    public static String extractMacFromKey(String key) {
        if (key == null || key.length() < 194) {
            throw new IllegalArgumentException("Invalid key length.");
        }

        StringBuilder macBuilder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int charIndex = 14 + i * 15;
            macBuilder.append(key.charAt(charIndex));
        }

        // Format as XX-XX-XX-XX-XX-XX (6 pairs)
        // Since MAC is 12 characters, group by 2
        String mac = macBuilder.toString();
        StringBuilder formattedMac = new StringBuilder();

        for (int i = 0; i < mac.length(); i += 2) {
            if (i > 0) {
                formattedMac.append("-");
            }
            formattedMac.append(mac.substring(i, i + 2));
        }

        return formattedMac.toString();
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
            return MacKeyEncryptor.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  boolean isLicenseValid(String license, String macAddress) {
        if (license == null || license.isEmpty()) {
            return false;
        }
        if (macAddress == null || macAddress.isEmpty()) {
            return false;
        }
        try {
            String extractedMac = MacKeyEncryptor.extractMacFromKey(license);
            return extractedMac != null && extractedMac.equals(macAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validateLicense() {
        String licenseKey =  "Consts.license_key";
        String macAddress = MacKeyEncryptor.getMacAddressSafe();
         return MacKeyEncryptor.isLicenseValid(licenseKey, macAddress);
    }
}
