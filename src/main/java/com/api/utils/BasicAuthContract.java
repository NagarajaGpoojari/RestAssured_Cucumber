package com.api.utils;

interface BasicAuthContract extends AuthProvider {
    void setCredentials(String username, String password);
}

interface JwtAuthContract extends AuthProvider {
    void setToken(String token);
}

interface OAuth2Contract extends AuthProvider {
    void setOAuthDetails(String tokenUrl, String clientId, String clientSecret);
}

interface CustomAuthContract extends AuthProvider {
    void setCustomHeader(String headerName, String token);
}
