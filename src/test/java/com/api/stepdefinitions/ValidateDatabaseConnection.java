package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.ConfigReader;
import com.api.utils.DBUtils;

import java.util.Map;

public class ValidateDatabaseConnection {

    private Response response;
    private DBUtils dbUtils;

    @Given("Database connection is established")
    public void databaseConnectionIsEstablished() {
    	ConfigReader config = new ConfigReader("config.properties");
        dbUtils = new DBUtils(config);
        dbUtils.connectToDB();
    }

    @Given("I send a GET request for user {int}")
    public void getUserFromAPI(int userId) {
        ConfigReader config = new ConfigReader("resources/config.properties");
        String baseUri = config.getProperty("baseUri");

        RestAssured.baseURI = baseUri;
        response = RestAssured.given()
                .pathParam("id", userId)
                .get("/api/users/{id}");
    }

    @Then("I validate the response with database for user {int}")
    public void validateResponseWithDB(int userId) {
        Map<String, String> dbData = dbUtils.getRowData(
                "SELECT name, job FROM users WHERE id = ?", userId
        );

        dbUtils.closeConnection();

        // Adjust JSON path keys based on actual API response structure
        String apiName = response.jsonPath().getString("data.first_name");
        String apiJob = response.jsonPath().getString("data.job");

        Assert.assertEquals(apiName, dbData.get("name"), "Name mismatch");
        Assert.assertEquals(apiJob, dbData.get("job"), "Job mismatch");
    }
}
