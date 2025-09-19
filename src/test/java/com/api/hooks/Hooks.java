package com.api.hooks;

import  io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	
	    @Before
	    public void beforeScenario() {
	        System.out.println("Starting scenario...");
	    }

	    @After
	    public void afterScenario() {
	        System.out.println("Ending scenario...");
	    }
	}



