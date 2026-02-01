package com.api.hooks;

import com.api.utils.AuthManager;
import com.api.utils.ILogger;
import com.api.utils.Log4jLogger;
import com.api.utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

public class Hooks {

    private final ILogger logger = new Log4jLogger();
    private final TestContext context = TestContext.get();
    private static String token;

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("\n=============== SCENARIO START ===============\n" +
                    " Name : " + scenario.getName() +
                    "\n==============================================");

        AuthManager.refreshToken();
        token = AuthManager.getToken();
    }

    @After
    public void afterScenario(Scenario scenario) {
        Response response = context.getResponse();

        if (scenario.isFailed()) {
            int statusCode = response != null ? response.getStatusCode() : -1;
            String actualBody = response != null ? response.getBody().asPrettyString() : "No response captured";

            logger.testFailure(
                scenario.getName(),
                "Expected status as per scenario step",
                actualBody,
                statusCode,
                new AssertionError("Scenario failed: " + scenario.getName())
            );
        } else {
            if (response != null) {
                logger.logResponse(
                    scenario.getName(),
                    response.getStatusCode(),
                    response.getBody().asPrettyString()
                );
            }
            logger.info("Scenario PASSED: " + scenario.getName());
        }

        logger.info("\n=============== SCENARIO END =================\n" +
                    " Name : " + scenario.getName() +
                    "\n==============================================");
    }

    public static String getToken() {
        return token;
    }
}
