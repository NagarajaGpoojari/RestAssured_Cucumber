package com.api.utils;

import io.restassured.specification.RequestSpecification;

/**
 * Contract for applying authentication to a RestAssured request.
 */
public interface AuthProvider {
    void applyAuth(RequestSpecification request);
}
