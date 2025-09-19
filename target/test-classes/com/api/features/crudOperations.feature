Feature: API CRUD Operations

  Scenario: Create a new user
    Given I set the POST endpoint
    When I send a POST request with user data
    Then I should receive a 201 status code

  Scenario: Read user details
    Given I set the GET endpoint for user "2"
    When I send a GET request
    Then I should receive a 200 status code

  Scenario: Update user details
    Given I set the PUT endpoint for user "2"
    When I send a PUT request with updated data
    Then I should receive a 200 status code

  Scenario: Delete a user
    Given I set the DELETE endpoint for user "2"
    When I send a DELETE request
    Then I should receive a 204 status code
