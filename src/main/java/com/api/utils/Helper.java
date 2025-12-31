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

    // Initialize base URI (always reset before setting)
    public static void init(String baseUri) {
        reset(); // ensures fresh spec for each test run
        requestSpec.set(RestAssured.given().baseUri(baseUri));
    }

    // Generic header setters
    public static void setHeaders(Map<String, String> headers) {
        requestSpec.get().headers(headers);
    }

    public static void setHeader(String key, String value) {
        requestSpec.get().header(key, value);
    }

    // Predefined header shortcuts
    public static void setJsonHeader() {
        requestSpec.get().header("Content-Type", "application/json");
    }

    public static void setXmlHeader() {
        requestSpec.get().header("Content-Type", "application/xml");
    }

    public static void setTextHeader() {
        requestSpec.get().header("Content-Type", "text/plain");
    }

    public static void setFormUrlEncodedHeader() {
        requestSpec.get().header("Content-Type", "application/x-www-form-urlencoded");
    }

    // Cookie support
    public static void setCookies(Map<String, String> cookies) {
        requestSpec.get().cookies(cookies);
    }

    // Auth token support
    public static void setAuthToken(String token) {
        requestSpec.get().header("Authorization", "Bearer " + token);
    }

    // Request body
    public static void setBody(Object body) {
        requestSpec.get().contentType(ContentType.JSON).body(body);
    }

    // HTTP methods with logging enabled
    public static Response get(String endpoint) {
        return requestSpec.get().log().all().when().get(endpoint).then().extract().response();
    }

    public static Response post(String endpoint) {
        return requestSpec.get().log().all().when().post(endpoint).then().extract().response();
    }

    public static Response put(String endpoint) {
        return requestSpec.get().log().all().when().put(endpoint).then().extract().response();
    }

    public static Response delete(String endpoint) {
        return requestSpec.get().log().all().when().delete(endpoint).then().extract().response();
    }

    public static Response patch(String endpoint) {
        return requestSpec.get().log().all().when().patch(endpoint).then().extract().response();
    }

    // Reset request spec
    public static void reset() {
        requestSpec.set(RestAssured.given());
    }
}
