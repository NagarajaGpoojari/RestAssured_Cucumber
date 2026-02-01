package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import com.api.utils.*;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenSteps {

    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();
    private Response response;
    private Map<String, String> credentials;
    private String endpoint;

    @Given("I authenticate with username {string} and password {string}")
    public void authenticate(String username, String password) {
        // Use UniversalAuthProvider directly (Hooks already initialized global auth)
        UniversalAuthProvider provider = new UniversalAuthProvider();

        if (username != null && !username.isEmpty()) {
            provider.setCredentials(username, password);
        }

        Helper.setAuthProvider(provider);
        logger.info("Authentication initialized with provided credentials");
        
        // Store credentials for request body
        credentials = new HashMap<>();
        credentials.put("username", username == null ? "" : username);
        credentials.put("password", password == null ? "" : password);
    }

    @When("I send a POST request to create an auth token at {string}")
    public void sendAuthRequest(String authUrl) {
        String baseUri = authUrl.split("/auth")[0];
        Helper.init(baseUri);
        endpoint = authUrl.substring(baseUri.length());

        Helper.setJsonHeader();
        Helper.setBody(credentials);

        response = RetryHelper.retry(() -> Helper.post(endpoint), 3, 2000);
        context.setResponse(response);
        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("the auth response status should be {int}")
    public void validateAuthStatus(int expectedStatus) {
        int actualStatus = context.getResponse().getStatusCode();
        logger.info("Validating auth response status: Expected = " + expectedStatus + ", Actual = " + actualStatus);
        Assert.assertEquals(actualStatus, expectedStatus,
                "Expected status " + expectedStatus + " but got " + actualStatus);
    }

    @Then("the auth response should contain {string}")
    public void validateAuthResponseContains(String expectedField) {
        String body = context.getResponse().getBody().asString();
        logger.debug("Validating auth response contains field: " + expectedField);
        Assert.assertTrue(body.contains(expectedField),
                "Auth response does not contain expected field: " + expectedField);

        // Store token if present
        if (expectedField.equalsIgnoreCase("token")) {
            String token = context.getResponse().jsonPath().getString("token");
            TestContext.get().setToken(token);
            logger.info("Token stored in TestContext: " + token);
        }
    }
}
