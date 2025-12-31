package com.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		     
        features = "src/test/java/com/api/features",   // path to feature files
        glue = {"com.api.stepdefinitions"},            // step definitions package
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" //Allure report Generator by using TestNG
        },
        monochrome = true,
        dryRun = false,                               	 // executes only @RestfuleBooker tags Scenarios
  //      tags = "@RestfuleBooker"                		 // executes Both @RestfuleBooker  and @ReqresTests tags Scenarios
  //	  tags = "@RestfuleBooker and @ReqresTests"      
        tags = "(@RestfuleBooker or @ReqresTests) and not @Ignore" 
        		// executes Both @RestfuleBooker  and @ReqresTests tags Scenarios but Ignores @Ignore tags 

		)
public class TestRunner extends AbstractTestNGCucumberTests {
	
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
