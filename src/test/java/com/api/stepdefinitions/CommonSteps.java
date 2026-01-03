package com.api.stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.*;

import java.util.Map;

public class CommonSteps {

    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        Response response = context.getResponse();
        int actualStatusCode = response.getStatusCode();
        logger.info("Validating status code: Expected = " + expectedStatusCode + ", Actual = " + actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
    }

    @Then("the response body should contain field {string}")
    public void validateResponseContainsField(String expectedField) {
        Response response = context.getResponse();
        String responseBody = response.getBody().asString();
        logger.debug("Checking if response contains field: " + expectedField);
        Assert.assertTrue(responseBody.contains(expectedField),
                "Response body does not contain expected field: " + expectedField);
    }

    @Then("the response should include a non-empty list")
    public void validateNonEmptyList() {
        Response response = context.getResponse();
        logger.debug("Checking if response contains a non-empty list");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0,
                "Expected non-empty list but response list is empty");
    }

    @Then("the response should match database records for user {int}")
    public void validateResponseWithDatabase(int userId) {
        ConfigReader config = new ConfigReader("config.properties");
        DBUtils dbUtils = new DBUtils(config);
        dbUtils.connectToDB();

        Response response = context.getResponse();
        String apiName = response.jsonPath().getString("data.first_name");
        String apiJob = response.jsonPath().getString("data.job");

        Map<String, String> dbData = dbUtils.getRowData("SELECT name, job FROM users WHERE id = ?", userId);
        dbUtils.closeConnection();

        logger.debug("Comparing API vs DB for user ID: " + userId);
        logger.debug("API -> Name: " + apiName + ", Job: " + apiJob);
        logger.debug("DB  -> Name: " + dbData.get("name") + ", Job: " + dbData.get("job"));

        Assert.assertEquals(apiName, dbData.get("name"), "Name mismatch between API and DB");
        Assert.assertEquals(apiJob, dbData.get("job"), "Job mismatch between API and DB");

        logger.info("Validation successful for user ID: " + userId);
    }
}
