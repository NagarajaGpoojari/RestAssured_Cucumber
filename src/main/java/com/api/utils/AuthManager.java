package com.api.utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AuthManager {

	public static String fetchToken(String authUrl, String username, String password) {
		Response response = given()
				.header("Content-Type", "application/json")
				.body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
				.post(authUrl);

		return response.jsonPath().getString("token");
	}
}
