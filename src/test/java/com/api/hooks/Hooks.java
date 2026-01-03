package com.api.hooks;

import com.api.utils.ILogger;
import com.api.utils.Log4jLogger;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	private final ILogger logger = new Log4jLogger();

	@Before
	public void beforeScenario() {
		logger.info("Starting scenario execution...");
	}

	@After
	public void afterScenario() {
		logger.info("Scenario execution completed.");
	}
}
