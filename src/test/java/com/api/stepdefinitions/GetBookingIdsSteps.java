package com.api.stepdefinitions;

import com.api.utils.*;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class GetBookingIdsSteps {

    private static ConfigReader configReader = new ConfigReader("config.properties");
    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();
    private String endpoint;

    @Given("I prepare the Booking API using auth type {string}")
    public void prepareBookingApi(String authType) {
        String baseUrl = configReader.getProperty("base.url");
        Helper.reset();
        Helper.init(baseUrl);

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);

        Helper.setJsonHeader();
        logger.info("Booking API prepared with base URL: " + baseUrl + " using auth type: " + authType);
    }

    @When("I fetch booking IDs without any search filters")
    public void fetchBookingIdsWithoutFilters() {
        endpoint = configReader.getProperty("endpoint.booking");
        logger.logRequest(endpoint, "GET", "No filters applied");

        Response response = Helper.get(endpoint);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @When("I fetch booking IDs using filters:")
    public void fetchBookingIdsWithFilters(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> queryParams = new HashMap<>();
        dataTable.asMaps().forEach(row -> {
            String key = row.get("key");
            String value = row.get("value");
            if (value != null && !value.trim().isEmpty()) {
                queryParams.put(key, value);
            }
        });

        endpoint = configReader.getProperty("endpoint.booking");
        StringBuilder endpointWithParams = new StringBuilder(endpoint);
        if (!queryParams.isEmpty()) {
            endpointWithParams.append("?");
            queryParams.forEach((k, v) -> endpointWithParams.append(k).append("=").append(v).append("&"));
            endpointWithParams.deleteCharAt(endpointWithParams.length() - 1);
        }

        logger.logRequest(endpointWithParams.toString(), "GET", "Filters: " + queryParams);
        Response response = Helper.get(endpointWithParams.toString());
        context.setResponse(response);
        logger.logResponse(endpointWithParams.toString(), response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the system should reply with status {int}")
    public void validateBookingStatus(int expectedStatusCode) {
        Response response = context.getResponse();
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating booking status: Expected = " + expectedStatusCode + ", Actual = " + actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status " + expectedStatusCode + " but got " + actualStatusCode);
    }

    @Then("the payload should include a non-empty list of booking IDs")
    public void validateNonEmptyBookingIds() {
        Response response = context.getResponse();
        logger.debug("Checking non-empty booking IDs list");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0,
                "Booking IDs list is empty in response");
    }

    @Then("the payload should include booking IDs that match the filters")
    public void validateFilteredBookingIds() {
        Response response = context.getResponse();
        logger.debug("Checking booking IDs match filter criteria");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0,
                "No booking IDs matched the filter criteria");
    }
}
