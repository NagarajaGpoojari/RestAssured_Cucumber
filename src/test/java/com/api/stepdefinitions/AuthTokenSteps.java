package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.Helper;
import com.api.utils.TestContext;

import java.util.HashMap;
import java.util.Map;

public class AuthTokenSteps {

    private Response response;
    private Map<String, String> credentials;

    @Given("I have username {string} and password {string}")
    public void iHaveUsernameAndPassword(String username, String password) {
        credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
    }

    @When("I send a POST request to create an auth token")
    public void iSendPostRequestToCreateAuthToken() {
        // Initialize base URI using Helper
        Helper.init("https://restful-booker.herokuapp.com");

        // Set request body
        Helper.setBody(credentials);

        // Perform POST request using Helper
        response = Helper.post("/auth");
    }

    @Then("I should receive a success response")
    public void iShouldReceiveSuccessResponse() {
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null");

        // Save token in TestContext for reuse
        TestContext.get().setToken(token);
        System.out.println("Token generated: " + token);
    }

    @Then("I should receive a failure response")
    public void iShouldReceiveFailureResponse() {
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected failure but got success");
        System.out.println("Token not generated. Status code: " + response.getStatusCode());
    }
}
