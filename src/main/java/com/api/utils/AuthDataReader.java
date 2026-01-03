package com.api.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AuthDataReader {

    public static Map<String, String> getAuthData(String authType) {
        Map<String, String> authDetails = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/auth_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("authType")) continue; // skip header
                String[] values = line.split(",");
                if (values[0].equalsIgnoreCase(authType)) {
                    authDetails.put("username", values[1]);
                    authDetails.put("password", values[2]);
                    authDetails.put("tokenUrl", values[3]);
                    authDetails.put("clientId", values[4]);
                    authDetails.put("clientSecret", values[5]);
                    authDetails.put("token", values[6]);
                    authDetails.put("headerName", values[7]);
                    authDetails.put("headerValue", values[8]);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authDetails;
    }
}
