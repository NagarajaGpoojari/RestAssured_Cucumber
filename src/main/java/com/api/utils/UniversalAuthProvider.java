package com.api.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Base64;

import static io.restassured.RestAssured.given;

/**
 * UniversalAuthProvider implements all authentication contracts.
 * Reusable for Basic, JWT, OAuth2, and Custom header-based auth.
 */
public class UniversalAuthProvider implements BasicAuthContract, JwtAuthContract, OAuth2Contract, CustomAuthContract {

    private String username;
    private String password;
    private String jwtToken;
    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String customHeaderName;
    private String customToken;

    // ---------------- BASIC AUTH ----------------
    @Override
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String getBasicAuthHeader() {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    // ---------------- JWT / BEARER ----------------
    @Override
    public void setToken(String token) {
        this.jwtToken = token;
    }

    private String getBearerToken() {
        return "Bearer " + jwtToken;
    }

    // ---------------- OAUTH2 ----------------
    @Override
    public void setOAuthDetails(String tokenUrl, String clientId, String clientSecret) {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private String fetchOAuth2Token() {
        Response response = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .post(tokenUrl);

        return response.jsonPath().getString("access_token");
    }

    // ---------------- CUSTOM HEADER ----------------
    @Override
    public void setCustomHeader(String headerName, String token) {
        this.customHeaderName = headerName;
        this.customToken = token;
    }

    // ---------------- APPLY AUTH ----------------
    @Override
    public void applyAuth(RequestSpecification request) {
        if (username != null && password != null) {
            request.header("Authorization", getBasicAuthHeader());
        } else if (jwtToken != null) {
            request.header("Authorization", getBearerToken());
        } else if (clientId != null && clientSecret != null) {
            String accessToken = fetchOAuth2Token();
            request.header("Authorization", "Bearer " + accessToken);
        } else if (customHeaderName != null && customToken != null) {
            request.header(customHeaderName, customToken);
        }
    }
}
