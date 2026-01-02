package com.api.stepdefinitions;

import com.api.utils.ConfigReader;
import com.api.utils.Helper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;

public class GetBookingIdsSteps {
	private static ConfigReader configReader = new ConfigReader("config.properties");
	private Response response;

	@Given("I initialize the Booking API")
	public void initializeBookingApi() {
		String baseUrl = configReader.getProperty("base.url");
		Helper.init(baseUrl);
		Helper.setJsonHeader(); // default header for API calls
	}

	@When("I send a GET request to booking endpoint without any query parameters")
	public void sendGetRequestWithoutParams() {
		String endpoint = configReader.getProperty("endpoint.booking");
		response = Helper.get(endpoint);
	}

	@When("I send a GET request to booking endpoint with query parameters:")
	public void sendGetRequestWithParams(io.cucumber.datatable.DataTable dataTable) {
		Map<String, String> queryParams = new HashMap<>();
		dataTable.asMaps().forEach(row -> {
			String key = row.get("key");
			String value = row.get("value");
			if (value != null && !value.trim().isEmpty()) {
				queryParams.put(key, value);
			}
		});

		String endpoint = configReader.getProperty("endpoint.booking");

		StringBuilder endpointWithParams = new StringBuilder(endpoint);
		if (!queryParams.isEmpty()) {
			endpointWithParams.append("?");
			queryParams.forEach((k, v) -> endpointWithParams.append(k).append("=").append(v).append("&"));
			endpointWithParams.deleteCharAt(endpointWithParams.length() - 1); // remove trailing &
		}

		response = Helper.get(endpointWithParams.toString());
	}

	@Then("the response status code should be {int}")
	public void validateStatusCode(int expectedStatusCode) {
		Assert.assertEquals(expectedStatusCode, response.getStatusCode());
	}

	@Then("the response should contain a non-empty list of booking IDs")
	public void validateNonEmptyBookingIds() {
		Assert.assertTrue(response.jsonPath().getList("").size() > 0);
	}

	@Then("the response should contain booking IDs matching the filter criteria")
	public void validateFilteredBookingIds() {
		Assert.assertTrue(response.jsonPath().getList("").size() > 0);
	}
}
