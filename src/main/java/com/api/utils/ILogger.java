package com.api.utils;

public interface ILogger {
	void info(String message);

	void warn(String message);

	void debug(String message);

	void error(String message, Throwable t);

	void testFailure(String testName, String expected, String actual, int statusCode, Throwable t);

	void logRequest(String endpoint, String method, String payload);

	void logResponse(String endpoint, int statusCode, String responseBody);
}
