Feature: User API Testing with Authentication

  @ReqresTests
  Scenario Outline: Validate User API responses
    Given I configure the user API resource "<endpoint>" with auth type "<authType>"
    When I perform a GET call on the user resource
    Then the system should deliver <statusCode> as the status
    And the body should include the field "<expectedField>"

    Examples:
      | authType | endpoint                          | statusCode | expectedField |
      | BASIC    | https://reqres.in/api/users?page=2 | 200        | page          |
      | BEARER   | https://reqres.in/api/users/2      | 200        | data          |
      | BASIC    | https://reqres.in/api/unknown/2    | 200        | data          |
