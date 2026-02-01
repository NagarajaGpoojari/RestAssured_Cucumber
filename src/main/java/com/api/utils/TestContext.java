package com.api.utils;

import io.restassured.response.Response;

public class TestContext {
    private static final ThreadLocal<TestContext> threadLocal = ThreadLocal.withInitial(TestContext::new);

    private String token;
    private Response response;
    private String endpoint;
    private String method;
    private String payload;

    public static TestContext get() {
        return threadLocal.get();
    }

    // ---------------- TOKEN ----------------
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    // ---------------- RESPONSE ----------------
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

    // ---------------- ENDPOINT ----------------
    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    // ---------------- METHOD ----------------
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    // ---------------- PAYLOAD ----------------
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }

    // ---------------- CLEAR ----------------
    public static void clear() {
        threadLocal.remove();
    }
}
