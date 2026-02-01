package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import com.api.utils.*;

import java.util.Map;
import org.testng.Assert;

public class CRUDOperationsSteps {

	private final TestContext context = TestContext.get();
	private String endpoint;

	@Given("I set up the POST endpoint")
	public void preparePostResource() {
		Helper.reset();
		Helper.init("baseUrl");
		endpoint = "/api/users";
	}

	@When("I send a POST request with user data")
	public void triggerPostCall(DataTable dataTable) {
		Map<String, String> userData = dataTable.asMap(String.class, String.class);
		Helper.setJsonHeader();
		Helper.setBody(userData);

		Response response = Helper.postWithContext(endpoint, context);
	}

	@Given("I set up the GET endpoint for user {string}")
	public void configureGetResource(String userId) {
		Helper.reset();
		Helper.init("baseUrl");
		endpoint = "/api/users/" + userId;
	}

	@When("I send a GET request")
	public void executeGetCall() {
		Helper.setJsonHeader();
		Response response = Helper.getWithContext(endpoint, context);
	}

	@Given("I set up the PUT endpoint for user {string}")
	public void setupPutResource(String userId) {
		Helper.reset();
		Helper.init("baseUrl");
		endpoint = "/api/users/" + userId;
	}

	@When("I send a PUT request with updated data")
	public void performPutCall(DataTable dataTable) {
		Map<String, String> updatedData = dataTable.asMap(String.class, String.class);
		Helper.setJsonHeader();
		Helper.setBody(updatedData);

		Response response = Helper.putWithContext(endpoint, context);
	}

	@Given("I set up the DELETE endpoint for user {string}")
	public void defineDeleteResource(String userId) {
		Helper.reset();
		Helper.init("baseUrl");
		endpoint = "/api/users/" + userId;
	}

	@When("I send a DELETE request")
	public void initiateDeleteCall() {
		Helper.setJsonHeader();
		Response response = Helper.deleteWithContext(endpoint, context);
	}

	@Then("the response status should be {int}")
	public void validateResponseStatus(int expectedStatus) {
		Assert.assertEquals(context.getResponse().getStatusCode(), expectedStatus,
				"Expected " + expectedStatus + " but got " + context.getResponse().getStatusCode());
	}
}
