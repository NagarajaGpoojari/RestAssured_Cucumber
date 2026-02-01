package com.api.utils;

import io.restassured.RestAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Helper {

    private static ThreadLocal<RequestSpecification> requestSpec =
            ThreadLocal.withInitial(() -> RestAssured.given());

    private static AuthProvider authProvider;

    private static final ConfigReader configReader = new ConfigReader("config.properties");

    // ---------------- INIT ----------------
    public static void init(String baseUriKey) {
        reset();
        String baseUri = configReader.getProperty(baseUriKey);
        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException("Base URI not found for key: " + baseUriKey);
        }
        requestSpec.set(RestAssured.given().baseUri(baseUri));
    }

    public static void setEndpoint(String endpointKey) {
        String endpoint = configReader.getProperty(endpointKey);
        if (endpoint == null || endpoint.isEmpty()) {
            throw new RuntimeException("Endpoint not found for key: " + endpointKey);
        }
        RestAssured.basePath = endpoint;
    }

    public static void setAuthProvider(AuthProvider provider) {
        authProvider = provider;
    }

    private static void applyAuth() {
        if (authProvider != null) {
            authProvider.applyAuth(requestSpec.get());
        }
    }

    // ---------------- HEADER METHODS ----------------
    public static void setHeaders(Map<String, String> headers) {
        requestSpec.get().headers(headers);
    }

    public static void setHeader(String key, String value) {
        requestSpec.get().header(key, value);
    }

    public static void setJsonHeader() {
        requestSpec.get().header("Content-Type", "application/json");
    }

    public static void setXmlHeader() {
        requestSpec.get().header("Content-Type", "application/xml");
    }

    public static void setFormUrlEncodedHeader() {
        requestSpec.get().header("Content-Type", "application/x-www-form-urlencoded");
    }

    // ---------------- NEW CONVENIENCE METHODS ----------------
    public static void withHeaders(Map<String, String> headers) {
        requestSpec.get().headers(headers);
    }

    public static void withoutHeaders() {
        requestSpec.set(RestAssured.given().baseUri(RestAssured.baseURI));
    }

    public static void withCookies(Map<String, String> cookies) {
        requestSpec.get().cookies(cookies);
    }

    public static void withoutCookies() {
        requestSpec.get().cookies((Map<String, String>) null);
    }

    // ---------------- BODY ----------------
    public static void setBody(Object body) {
        requestSpec.get().contentType(ContentType.JSON).body(body);
    }

    // ---------------- CORE REQUEST EXECUTION ----------------
    private static Response executeWithRetry(RequestSpecification spec, String method, String endpoint) {
        applyAuth();
        Response response;

        switch (method.toUpperCase()) {
            case "GET":
                response = spec.log().all().when().get(endpoint).then().extract().response();
                break;
            case "POST":
                response = spec.log().all().when().post(endpoint).then().extract().response();
                break;
            case "PUT":
                response = spec.log().all().when().put(endpoint).then().extract().response();
                break;
            case "DELETE":
                response = spec.log().all().when().delete(endpoint).then().extract().response();
                break;
            case "PATCH":
                response = spec.log().all().when().patch(endpoint).then().extract().response();
                break;
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }

        // Retry only if failed
        if (response.getStatusCode() == 401 || response.getStatusCode() == 409 || response.getStatusCode() == 429) {
            response = RetryHelper.retry(() -> {
                switch (method.toUpperCase()) {
                    case "GET": return spec.when().get(endpoint).then().extract().response();
                    case "POST": return spec.when().post(endpoint).then().extract().response();
                    case "PUT": return spec.when().put(endpoint).then().extract().response();
                    case "DELETE": return spec.when().delete(endpoint).then().extract().response();
                    case "PATCH": return spec.when().patch(endpoint).then().extract().response();
                    default: throw new IllegalArgumentException("Unsupported method: " + method);
                }
            }, 3, 2000);
        }

        return response;
    }

    // ---------------- PUBLIC METHODS ----------------
    public static Response get(String endpoint) { return executeWithRetry(requestSpec.get(), "GET", endpoint); }
    public static Response post(String endpoint) { return executeWithRetry(requestSpec.get(), "POST", endpoint); }
    public static Response put(String endpoint) { return executeWithRetry(requestSpec.get(), "PUT", endpoint); }
    public static Response delete(String endpoint) { return executeWithRetry(requestSpec.get(), "DELETE", endpoint); }
    public static Response patch(String endpoint) { return executeWithRetry(requestSpec.get(), "PATCH", endpoint); }

    // ---------------- CONTEXT-AWARE METHODS ----------------
    public static Response postWithContext(String endpoint, TestContext context) {
        Response response = post(endpoint);
        context.setResponse(response);
        return response;
    }

    public static Response getWithContext(String endpoint, TestContext context) {
        Response response = get(endpoint);
        context.setResponse(response);
        return response;
    }

    public static Response putWithContext(String endpoint, TestContext context) {
        Response response = put(endpoint);
        context.setResponse(response);
        return response;
    }

    public static Response deleteWithContext(String endpoint, TestContext context) {
        Response response = delete(endpoint);
        context.setResponse(response);
        return response;
    }

    public static Response patchWithContext(String endpoint, TestContext context) {
        Response response = patch(endpoint);
        context.setResponse(response);
        return response;
    }
    

    public static void reset() {
        requestSpec.set(RestAssured.given());
    }
}
