package com.api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
		
        features = "src/test/java/com/api/features/crudOperations.feature",   
        glue = {
        		"com.api.stepdefinitions",
        		"com.api.hooks"
        		},            
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" 
        },
        monochrome = true,
        dryRun = false,                               	 // executes only @RestfuleBooker tags Scenarios
    //    tags = "@RestfuleBooker"                		 // executes Both @RestfuleBooker  and @ReqresTests tags Scenarios
  	//  	  tags = "@ReqresTests"      
          tags = "(@RestfuleBooker or @ReqresTests) and not @Ignore" 
        		
		)
public class TestRunner extends AbstractTestNGCucumberTests {
	
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
