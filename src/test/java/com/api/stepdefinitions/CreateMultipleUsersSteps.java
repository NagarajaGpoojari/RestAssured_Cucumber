package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.testng.Assert;
import com.api.utils.*;

import java.util.List;
import java.util.Map;

public class CreateMultipleUsersSteps {

    private final TestContext context = TestContext.get();
    private final ILogger logger = new Log4jLogger();
    private Response response;
    private String endpoint;

    @Given("I prepare the bulk user creation resource")
    public void prepareBulkUserCreationResource() {
        Helper.reset();
        Helper.init("baseUrl");              
        Helper.setEndpoint("userEndpoint");
        endpoint = new ConfigReader("C:\\Users\\Admin\\git\\RestAssured_Cucumber\\src\\test\\resources\\config.properties").getProperty("userEndpoint");
        
        logger.info("Bulk user creation resource prepared using configReader");
    }

    @When("I dispatch POST calls for each user entry")
    public void dispatchPostCallsForUsers(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> userData : users) {
            Helper.setJsonHeader();
            Helper.setBody(userData);
            response = Helper.postWithContext(endpoint, context);
            logger.logResponse(endpoint, response.getStatusCode(), response.getBody().asPrettyString());
            Assert.assertEquals(response.getStatusCode(), 201,
                    "Expected status code 201 but got " + response.getStatusCode());
        }
    }

    @Then("the system should confirm each creation with status {int}")
    public void validateMultipleUsersCreation(int expectedStatusCode) {
        int actualStatusCode = context.getResponse().getStatusCode();
        logger.info("Validating bulk user creation: Expected = " + expectedStatusCode + ", Actual = " + actualStatusCode);
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
    }
}
