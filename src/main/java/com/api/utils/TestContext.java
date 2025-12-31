package com.api.utils;

public class TestContext {
	private static final ThreadLocal<TestContext> threadLocal = ThreadLocal.withInitial(TestContext::new);

	private String token;

	public static TestContext get() {
		return threadLocal.get();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static void clear() {
		threadLocal.remove();
	}
}
