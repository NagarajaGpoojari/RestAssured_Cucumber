package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.*;

import java.util.Map;

public class ValidateDatabaseConnection {

    private Response response;
    private DBUtils dbUtils;
    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();
    private String endpoint;

    @Given("I establish a connection to the database")
    public void establishDatabaseConnection() {
        ConfigReader config = new ConfigReader("config.properties");
        dbUtils = new DBUtils(config);
        dbUtils.connectToDB();
        logger.info("Database connection established successfully.");
    }

    @When("I retrieve user details from API for ID {int} using auth type {string}")
    public void retrieveUserDetailsFromApi(int userId, String authType) {
        ConfigReader config = new ConfigReader("resources/config.properties");
        String baseUri = config.getProperty("reqresbaseUri");
        endpoint = "/api/users/" + userId;

        Helper.reset();
        Helper.init(baseUri);

        UniversalAuthProvider provider = AuthManagerFactory.getAuthProvider(authType);
        Helper.setAuthProvider(provider);

        logger.logRequest(endpoint, "GET", "Fetching user details for ID: " + userId);

        // Retry logic applied here
        response = RetryHelper.retry(() -> Helper.get(endpoint), 3, 1000);
        context.setResponse(response);

        logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
    }

    @Then("I cross-verify the API response with database records for ID {int}")
    public void crossVerifyApiResponseWithDatabase(int userId) {
        Map<String, String> dbData = dbUtils.getRowData(
                "SELECT name, job FROM users WHERE id = ?", userId
        );

        dbUtils.closeConnection();
        logger.info("Database connection closed after fetching data for user ID: " + userId);

        String apiName = response.jsonPath().getString("data.first_name");
        String apiJob = response.jsonPath().getString("data.job");

        logger.debug("Comparing API response with DB data for user ID: " + userId);
        logger.debug("API -> Name: " + apiName + ", Job: " + apiJob);
        logger.debug("DB  -> Name: " + dbData.get("name") + ", Job: " + dbData.get("job"));

        Assert.assertEquals(apiName, dbData.get("name"), "Name mismatch between API and DB");
        Assert.assertEquals(apiJob, dbData.get("job"), "Job mismatch between API and DB");

        logger.info("Validation successful for user ID: " + userId);
    }
}
