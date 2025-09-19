package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

public class CRUD_Operations_StepDef {

	private Response response;
	private String endpoint;

	@Given("I set the POST endpoint")
	public void setPostEndpoint() {
		endpoint = "https://reqres.in/api/users";
	}

	@When("I send a POST request with user data")
	public void sendPostRequest() {
		Map<String, String> userData = new HashMap<>();
		userData.put("name", "Nagaraja");
		userData.put("job", "Test Automation Engineer");

		response = RestAssured.given()
				.header("Content-Type", "application/json")
				.body(userData)
				.post(endpoint);
	}

	@Given("I set the GET endpoint for user {string}")
	public void setGetEndpoint(String userId) {
		endpoint = "https://reqres.in/api/users/" + userId;
	}

	@When("I send a GET request")
	public void sendGetRequest() {
		response = RestAssured.given().get(endpoint);
	}

	@Given("I set the PUT endpoint for user {string}")
	public void setPutEndpoint(String userId) {
		endpoint = "https://reqres.in/api/users/" + userId;
	}

	@When("I send a PUT request with updated data")
	public void sendPutRequest() {
		Map<String, String> updatedData = new HashMap<>();
		updatedData.put("name", "Nagaraja Updated");
		updatedData.put("job", "Senior Test Engineer");

		response = RestAssured.given()
				.header("Content-Type", "application/json")
				.body(updatedData)
				.put(endpoint);
	}

	@Given("I set the DELETE endpoint for user {string}")
	public void setDeleteEndpoint(String userId) {
		endpoint = "https://reqres.in/api/users/" + userId;
	}

	@When("I send a DELETE request")
	public void sendDeleteRequest() {
		response = RestAssured.given().delete(endpoint);
	}

	@Then("I should receive a {int} status code")
	public void validateStatusCode(int expectedStatusCode) {
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
	}
}
