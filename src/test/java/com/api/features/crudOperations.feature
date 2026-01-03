Feature: API CRUD Operations with Authentication

  @ReqresTests
  Scenario Outline: Create a new user
    Given I prepare the POST resource with auth type "<authType>"
    When I trigger a POST call using user details
      | name   | job   |
      | <name> | <job> |
    Then the service should return <statusCode> as the outcome

    Examples:
      | authType | name      | job                     | statusCode |
      | BASIC    | Nagaraja  | Test Automation Engineer| 201        |
      | BASIC    | John Doe  | Developer               | 201        |

  @ReqresTests
  Scenario Outline: Read user details
    Given I configure the GET resource for user "<userId>" with auth type "<authType>"
    When I execute a GET call
    Then the service should respond with <statusCode>

    Examples:
      | authType | userId | statusCode |
      | BEARER   | 2      | 200        |
      | BEARER   | 5      | 200        |

  @ReqresTests
  Scenario Outline: Update user details
    Given I set up the PUT resource for user "<userId>" with auth type "<authType>"
    When I perform a PUT call with modified data
      | name   | job   |
      | <name> | <job> |
    Then the service should acknowledge with <statusCode>

    Examples:
      | authType | userId | name             | job                  | statusCode |
      | BASIC    | 2      | Nagaraja Updated | Senior Test Engineer | 200        |
      | BASIC    | 5      | John Updated     | Lead Developer       | 200        |

  @ReqresTests
  Scenario Outline: Delete a user
    Given I define the DELETE resource for user "<userId>" with auth type "<authType>"
    When I initiate a DELETE call
    Then the service should confirm with <statusCode>

    Examples:
      | authType | userId | statusCode |
      | BEARER   | 2      | 204        |
      | BEARER   | 5      | 204        |
