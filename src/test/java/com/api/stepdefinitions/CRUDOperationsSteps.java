package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import com.api.utils.*;

import java.util.Map;

import org.testng.Assert;

public class CRUDOperationsSteps {

    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();
    private String endpoint;

    // ---------------- CREATE ----------------
    @Given("I prepare the POST resource with auth type {string}")
    public void preparePostResource(String authType) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users";

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);
        logger.info("Auth applied for POST resource using type: " + authType);
    }

    @When("I trigger a POST call using user details")
    public void triggerPostCall(DataTable dataTable) {
        Map<String, String> userData = dataTable.asMap(String.class, String.class);
        Helper.setJsonHeader();
        Helper.setBody(userData);

        Response response = Helper.post(endpoint);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the service should return {int} as the outcome")
    public void validatePostOutcome(int expectedStatus) {
        int actualStatus = context.getResponse().getStatusCode();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected " + expectedStatus + " but got " + actualStatus);
    }

    // ---------------- READ ----------------
    @Given("I configure the GET resource for user {string} with auth type {string}")
    public void configureGetResource(String userId, String authType) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);
        logger.info("Auth applied for GET resource using type: " + authType);
    }

    @When("I execute a GET call")
    public void executeGetCall() {
        Helper.setJsonHeader();
        Response response = Helper.get(endpoint);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the service should respond with {int}")
    public void validateGetResponse(int expectedStatus) {
        int actualStatus = context.getResponse().getStatusCode();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected " + expectedStatus + " but got " + actualStatus);
    }

    // ---------------- UPDATE ----------------
    @Given("I set up the PUT resource for user {string} with auth type {string}")
    public void setupPutResource(String userId, String authType) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);
        logger.info("Auth applied for PUT resource using type: " + authType);
    }

    @When("I perform a PUT call with modified data")
    public void performPutCall(DataTable dataTable) {
        Map<String, String> updatedData = dataTable.asMap(String.class, String.class);
        Helper.setJsonHeader();
        Helper.setBody(updatedData);

        Response response = Helper.put(endpoint);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the service should acknowledge with {int}")
    public void validatePutResponse(int expectedStatus) {
        int actualStatus = context.getResponse().getStatusCode();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected " + expectedStatus + " but got " + actualStatus);
    }

    // ---------------- DELETE ----------------
    @Given("I define the DELETE resource for user {string} with auth type {string}")
    public void defineDeleteResource(String userId, String authType) {
        Helper.reset();
        Helper.init("https://reqres.in");
        endpoint = "/api/users/" + userId;

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);
        logger.info("Auth applied for DELETE resource using type: " + authType);
    }

    @When("I initiate a DELETE call")
    public void initiateDeleteCall() {
        Helper.setJsonHeader();
        Response response = Helper.delete(endpoint);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the service should confirm with {int}")
    public void validateDeleteResponse(int expectedStatus) {
        int actualStatus = context.getResponse().getStatusCode();
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected " + expectedStatus + " but got " + actualStatus);
    }
}
