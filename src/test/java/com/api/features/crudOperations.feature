Feature: API CRUD Operations with Authentication
  This feature validates Create, Read, Update, and Delete operations on Reqres API.

  @ReqresTests
  Scenario Outline: Create a new user with valid details
    Given I set up the POST endpoint
    When I send a POST request with user data
      | name   | job   |
      | <name> | <job> |
    Then the response status should be <statusCode>

    Examples:
      | name      | job                     | statusCode |
      | Nagaraja  | Test Automation Engineer| 201        |
      | John Doe  | Developer               | 201        |

  @ReqresTests
  Scenario Outline: Retrieve user details
    Given I set up the GET endpoint for user "<userId>"
    When I send a GET request
    Then the response status should be <statusCode>

    Examples:
      | userId | statusCode |
      | 2      | 200        |
      | 5      | 200        |

  @ReqresTests
  Scenario Outline: Update existing user details
    Given I set up the PUT endpoint for user "<userId>"
    When I send a PUT request with updated data
      | name   | job   |
      | <name> | <job> |
    Then the response status should be <statusCode>

    Examples:
      | userId | name             | job                  | statusCode |
      | 2      | Nagaraja Updated | Senior Test Engineer | 200        |
      | 5      | John Updated     | Lead Developer       | 200        |

  @ReqresTests
  Scenario Outline: Delete a user
    Given I set up the DELETE endpoint for user "<userId>"
    When I send a DELETE request
    Then the response status should be <statusCode>

    Examples:
      | userId | statusCode |
      | 2      | 204        |
      | 5      | 204        |
