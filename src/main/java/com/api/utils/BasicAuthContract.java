package com.api.utils;

interface BasicAuthContract extends AuthProvider {
    void setCredentials(String username, String password);
}

interface JwtAuth extends AuthProvider {
    void setToken(String token);
}

interface OAuth2 extends AuthProvider {
    void setOAuthDetails(String tokenUrl, String clientId, String clientSecret);
}

interface CustomAuth extends AuthProvider {
    void setCustomHeader(String headerName, String token);
}
