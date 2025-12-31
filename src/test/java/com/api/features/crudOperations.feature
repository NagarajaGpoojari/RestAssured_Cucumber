Feature: API CRUD Operations

  @ReqresTests
  Scenario Outline: Create a new user
    Given I set the POST endpoint
    When I send a POST request with user data
      | name   | job   |
      | <name> | <job> |
    Then I should receive a <statusCode> status code

    Examples:
      | name      | job                     | statusCode |
      | Nagaraja  | Test Automation Engineer| 201        |
      | John Doe  | Developer               | 201        |

  @ReqresTests
  Scenario Outline: Read user details
    Given I set the GET endpoint for user "<userId>"
    When I send a GET request
    Then I should receive a <statusCode> status code

    Examples:
      | userId | statusCode |
      | 2      | 200        |
      | 5      | 200        |

  @ReqresTests
  Scenario Outline: Update user details
    Given I set the PUT endpoint for user "<userId>"
    When I send a PUT request with updated data
      | name   | job   |
      | <name> | <job> |
    Then I should receive a <statusCode> status code

    Examples:
      | userId | name             | job                  | statusCode |
      | 2      | Nagaraja Updated | Senior Test Engineer | 200        |
      | 5      | John Updated     | Lead Developer       | 200        |

  @ReqresTests
  Scenario Outline: Delete a user
    Given I set the DELETE endpoint for user "<userId>"
    When I send a DELETE request
    Then I should receive a <statusCode> status code

    Examples:
      | userId | statusCode |
      | 2      | 204        |
      | 5      | 204        |
