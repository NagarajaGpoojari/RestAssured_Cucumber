package com.api.utils;

import java.util.Map;

public class AuthManagerFactory {

    public static UniversalAuthProvider getAuthProvider(String authType) {
        Map<String, String> data = AuthDataReader.getAuthData(authType);
        UniversalAuthProvider provider = new UniversalAuthProvider();

        switch (authType.toUpperCase()) {
            case "BASIC":
                provider.setCredentials(data.get("username"), data.get("password"));
                break;
            case "BEARER":
                provider.setToken(data.get("token"));
                break;
            case "OAUTH2":
                provider.setOAuthDetails(data.get("tokenUrl"), data.get("clientId"), data.get("clientSecret"));
                break;
            case "CUSTOM":
                provider.setCustomHeader(data.get("headerName"), data.get("headerValue"));
                break;
        }
        return provider;
    }
}
