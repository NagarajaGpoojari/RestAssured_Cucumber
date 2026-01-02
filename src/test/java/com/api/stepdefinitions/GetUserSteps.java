package com.api.stepdefinitions;

import org.testng.Assert;
import com.api.utils.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class GetUserSteps {

	private Response response;
	private String endpoint;

	@Given("the API endpoint is {string}")
	public void setApiEndpoint(String endpoint) {
		String baseUri = endpoint.split("/api")[0]; //- https://reqres.in
		this.endpoint = endpoint.substring(baseUri.length()); //- /api/users?page=2
		Helper.init(baseUri);
	}

	@When("I send a GET request to the endpoint")
	public void sendGetRequest() {
		response = Helper.get(endpoint);
	}

	@Then("the response status code should be {int}")
	public void validateStatusCode(int expectedStatusCode) {
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
				"Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
	}

	@Then("the response should contain {string}")
	public void validateResponseContainsField(String expectedField) {
		String responseBody = response.getBody().asString();
		Assert.assertTrue(responseBody.contains(expectedField),
				"Response body does not contain expected field: " + expectedField);
	}
}
