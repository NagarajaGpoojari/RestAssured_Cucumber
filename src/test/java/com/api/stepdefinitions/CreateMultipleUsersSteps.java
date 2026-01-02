package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.Helper;

import java.util.List;
import java.util.Map;

public class CreateMultipleUsersSteps {

	private Response response;
	private String endpoint;

	@Given("I set the POST endpoint for creating users")
	public void setPostEndpointForUsers() {
		Helper.init("https://reqres.in");
		endpoint = "/api/users";
	}

	@When("I send POST requests for multiple users")
	public void sendPostRequestsForMultipleUsers(DataTable dataTable) {
		// Convert DataTable into List of Maps
		List<Map<String, String>> users = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> userData : users) {
			Helper.setJsonHeader();
			Helper.setBody(userData);
			response = Helper.post(endpoint);

			
			Assert.assertEquals(response.getStatusCode(), 201,
					"Expected status code 201 but got " + response.getStatusCode());
		}
	}

	@Then("each user should be created successfully with status code {int}")
	public void validateMultipleUsersCreation(int expectedStatusCode) {
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
				"Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
	}
}
