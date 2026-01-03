package com.api.utils;

import io.restassured.response.Response;

public class TestContext {
    private static final ThreadLocal<TestContext> threadLocal = ThreadLocal.withInitial(TestContext::new);

    private String token;
    private Response response;

    public static TestContext get() {
        return threadLocal.get();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static void clear() {
        threadLocal.remove();
    }
}
