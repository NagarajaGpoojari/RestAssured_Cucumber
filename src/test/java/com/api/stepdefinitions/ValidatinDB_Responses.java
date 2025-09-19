package com.api.stepdefinitions;

import java.util.Map;

import org.testng.Assert;
import io.qameta.allure.Step;
import com.api.utils.ConfigReader;
import com.api.utils.DBUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ValidatinDB_Responses {

    Response response;
    Map<String, String> dbData;

    @Given("Database connection is established")
    public void databaseConnectionIsEstablished() {
        DBUtils.connectToDB();
    }

    @Given("I send a GET request for user {int}")
    public void getUserFromAPI(int userId) {
        String baseUri = ConfigReader.getProperty("baseUri");
        RestAssured.baseURI = baseUri + "/api/users/" + userId;
        response = RestAssured.given().get();
    }

    @Then("I validate the response with database for user {int}")
    public void validateResponseWithDB(int userId) {
        dbData = DBUtils.getUserFromDB(userId);
        DBUtils.closeConnection();

        String apiName = response.jsonPath().getString("data.first_name"); // Adjust based on actual response
        String apiJob = response.jsonPath().getString("data.job");         // Adjust based on actual response

        Assert.assertEquals(apiName, dbData.get("name"), "Name mismatch");
        Assert.assertEquals(apiJob, dbData.get("job"), "Job mismatch");
    }
}
