package com.api.stepdefinitions;

import org.testng.Assert;
import com.api.utils.*;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class GetUserSteps {

    private final TestContext context = TestContext.get();
    private String endpoint;
    private final ILogger logger = new Log4jLogger();

    @Given("I configure the user API resource {string} with auth type {string}")
    public void configureUserApiResource(String fullUrl, String authType) {
        String baseUri = fullUrl.split("/api")[0];
        this.endpoint = fullUrl.substring(baseUri.length());

        Helper.reset();
        Helper.init(baseUri);

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);

        logger.info("User API configured with base URI: " + baseUri + " and auth type: " + authType);
        logger.info("Endpoint set: " + this.endpoint);
    }

    @When("I perform a GET call on the user resource")
    public void performGetCallOnUserResource() {
        logger.logRequest(endpoint, "GET", "User API call");
        try {
            Response response = Helper.get(endpoint);
            context.setResponse(response);
            logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
        } catch (Exception e) {
            logger.testFailure("GET Request", "200 OK", "Exception", 500, e);
            Assert.fail("GET request failed: " + e.getMessage());
        }
    }

    @Then("the system should deliver {int} as the status")
    public void validateUserStatusCode(int expectedStatusCode) {
        Response response = context.getResponse();
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating user API status: Expected = " + expectedStatusCode + ", Actual = " + actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
    }

    @Then("the body should include the field {string}")
    public void validateUserResponseContainsField(String expectedField) {
        Response response = context.getResponse();
        String responseBody = response.getBody().asString();
        logger.debug("Checking if response contains field: " + expectedField);
        Assert.assertTrue(responseBody.contains(expectedField),
                "Response body does not contain expected field: " + expectedField);
    }
}
