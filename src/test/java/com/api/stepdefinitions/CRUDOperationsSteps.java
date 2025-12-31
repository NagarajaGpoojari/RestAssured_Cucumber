package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.Helper;

import java.util.Map;

public class CRUDOperationsSteps {

    private Response response;
    private String endpoint;

    @Given("I set the POST endpoint")
    public void setPostEndpoint() {
        // Always reset before starting a new scenario
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users";
    }

    @When("I send a POST request with user data")
    public void sendPostRequest(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);

        Helper.setJsonHeader();
        Helper.setHeader("User-Agent", "Mozilla/5.0");
        Helper.setHeader("Accept", "*/*");
        Helper.setBody(userData);

        // Logging is already enabled in Helper methods
        response = Helper.post(endpoint);
    }

    @Given("I set the GET endpoint for user {string}")
    public void setGetEndpoint(String userId) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;
    }

    @When("I send a GET request")
    public void sendGetRequest() {
        Helper.setJsonHeader();
        Helper.setHeader("User-Agent", "Mozilla/5.0");
        Helper.setHeader("Accept", "*/*");
        response = Helper.get(endpoint); // logs request details
    }

    @Given("I set the PUT endpoint for user {string}")
    public void setPutEndpoint(String userId) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;
    }

    @When("I send a PUT request with updated data")
    public void sendPutRequest(DataTable dataTable) {
        Map<String, String> updatedData = dataTable.asMap(String.class, String.class);

        Helper.setJsonHeader();
        Helper.setHeader("User-Agent", "Mozilla/5.0");
        Helper.setHeader("Accept", "*/*");
        Helper.setBody(updatedData);

        response = Helper.put(endpoint); // logs request details
    }

    @Given("I set the DELETE endpoint for user {string}")
    public void setDeleteEndpoint(String userId) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;
    }

    @When("I send a DELETE request")
    public void sendDeleteRequest() {
        Helper.setJsonHeader();
        Helper.setHeader("User-Agent", "Mozilla/5.0");
        response = Helper.delete(endpoint); // logs request details
    }

    @Then("I should receive a {int} status code")
    public void validateStatusCode(int expectedStatusCode) {
        // Logs response status code for debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
    }
}
