package com.api.utils;

import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthManager {

	private static String token;
	private static final ConfigReader config = new ConfigReader("config.properties");

	public static String getToken() {
		if (token == null) {
			refreshToken();
		}
		return token;
	}

	public static void refreshToken() {
		// Priority: System properties > Excel > config.properties
		String authUrl = System.getProperty("authUrl");
		String username = System.getProperty("username");
		String password = System.getProperty("password");

		// If not set via System properties, try Excel
		if (username == null || password == null) {
			try {
				Map<String, String> creds = ExcelReader.getRow("auth_data.xlsx", "auth_data", "login");
				if (creds != null) {

					username = creds.getOrDefault("username", username);
					password = creds.getOrDefault("password", password);

					if (username != null)
						System.setProperty("username", username);
					if (password != null)
						System.setProperty("password", password);
				}
			} catch (Exception e) {
				System.out.println("Excel credentials not found, falling back to config.");
			}
		}

		// Fallback to config.properties if still missing
		if (authUrl == null)
			authUrl = config.getProperty("authUrl");
		if (username == null)
			username = config.getProperty("username");
		if (password == null)
			password = config.getProperty("password");

		if (authUrl == null || username == null || password == null) {
			throw new RuntimeException("Missing authentication details. Check config, Excel, or system properties.");
		}

		Response response = given().header("Content-Type", "application/json")
				.body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}").post(authUrl);

		if (response.statusCode() != 200) {
			throw new RuntimeException("Failed to fetch token. Status: " + response.statusCode() + "\nResponse: "
					+ response.asPrettyString());
		}

		token = response.jsonPath().getString("token");
		TestContext.get().setToken(token); // store in context for reuse/logging
	}
}
