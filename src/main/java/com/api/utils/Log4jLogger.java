package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jLogger implements ILogger {

    private static final Logger logger = LogManager.getLogger(Log4jLogger.class);

    @Override
    public void info(String message) {
        logger.info(" INFO: {}", message);
    }

    @Override
    public void warn(String message) {
        logger.warn(" WARNING: {}", message);
    }

    @Override
    public void debug(String message) {
        logger.debug(" DEBUG: {}", message);
    }

    @Override
    public void error(String message, Throwable t) {
        logger.error(" ERROR: {} | Exception: {}", message, t.getMessage(), t);
    }

    @Override
    public void testFailure(String testName, String expected, String actual, int statusCode, Throwable t) {
        logger.error("\n================= TEST FAILURE =================\n" +
                     " Test Name   : {}\n" +
                     " Expected    : {}\n" +
                     " Actual      : {}\n" +
                     " Status Code : {}\n" +
                     " Exception   : {}\n" +
                     "================================================",
                     testName, expected, actual, statusCode, t.getMessage(), t);
    }

    @Override
    public void logRequest(String endpoint, String method, String payload) {
        logger.info("\n----------------- API REQUEST -----------------\n" +
                    " Endpoint : {}\n" +
                    " Method   : {}\n" +
                    " Payload  : {}\n" +
                    "------------------------------------------------",
                    endpoint, method, payload);
    }

    @Override
    public void logResponse(String endpoint, int statusCode, String responseBody) {
        logger.info("\n----------------- API RESPONSE ----------------\n" +
                    " Endpoint   : {}\n" +
                    " StatusCode : {}\n" +
                    " Body       : {}\n" +
                    "------------------------------------------------",
                    endpoint, statusCode, responseBody);
    }
}
