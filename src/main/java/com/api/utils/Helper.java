package com.api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Helper {

    // ThreadLocal ensures thread safety for parallel executions
    private static ThreadLocal<RequestSpecification> requestSpec =
            ThreadLocal.withInitial(() -> RestAssured.given());

    // Current AuthProvider (Basic, JWT, OAuth2, Custom)
    private static AuthProvider authProvider;

    // Initialize base URI 
    public static void init(String baseUri) {
        reset(); // ensures fresh spec for each test run
        requestSpec.set(RestAssured.given().baseUri(baseUri));
    }

    // Apply authentication strategy
    public static void setAuthProvider(AuthProvider provider) {
        authProvider = provider;
    }

    private static void applyAuth() {
        if (authProvider != null) {
            authProvider.applyAuth(requestSpec.get());
        }
    }

    public static void setHeaders(Map<String, String> headers) {
        requestSpec.get().headers(headers);
    }

    public static void setHeader(String key, String value) {
        requestSpec.get().header(key, value);
    }

    // Predefined header 
    public static void setJsonHeader() {
        requestSpec.get().header("Content-Type", "application/json");
    }

    public static void setXmlHeader() {
        requestSpec.get().header("Content-Type", "application/xml");
    }

    public static void setFormUrlEncodedHeader() {
        requestSpec.get().header("Content-Type", "application/x-www-form-urlencoded");
    }

    public static void setCookies(Map<String, String> cookies) {
        requestSpec.get().cookies(cookies);
    }

    // Request body
    public static void setBody(Object body) {
        requestSpec.get().contentType(ContentType.JSON).body(body);
    }

    // ---------------- REQUEST METHODS WITH AUTH + RETRY ----------------
    public static Response get(String endpoint) {
        applyAuth();
        return RetryHelper.retry(() ->
                requestSpec.get().log().all().when().get(endpoint).then().extract().response(),
                3, 1000);
    }

    public static Response post(String endpoint) {
        applyAuth();
        return RetryHelper.retry(() ->
                requestSpec.get().log().all().when().post(endpoint).then().extract().response(),
                3, 1000);
    }

    public static Response put(String endpoint) {
        applyAuth();
        return RetryHelper.retry(() ->
                requestSpec.get().log().all().when().put(endpoint).then().extract().response(),
                3, 1000);
    }

    public static Response delete(String endpoint) {
        applyAuth();
        return RetryHelper.retry(() ->
                requestSpec.get().log().all().when().delete(endpoint).then().extract().response(),
                3, 1000);
    }

    public static Response patch(String endpoint) {
        applyAuth();
        return RetryHelper.retry(() ->
                requestSpec.get().log().all().when().patch(endpoint).then().extract().response(),
                3, 1000);
    }

    public static void reset() {
        requestSpec.set(RestAssured.given());
    }
}
