package com.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
	    features = ".\\src\\test\\java\\com\\api\\features",
	    		
	    glue = {"com.api.stepdefinitions"},
	    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
	    monochrome = true,
	    dryRun=true
	  //  tags="@Smoke or @Regression"
	    
	)


	public class TestRunner  extends AbstractTestNGCucumberTests {
		
		


}
