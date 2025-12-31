Feature: User API Testing
  @ReqresTests
  Scenario Outline: Validate User API responses
    Given the API endpoint is "<endpoint>"
    When I send a GET request to the endpoint
    Then the response status code should be <statusCode>
    And the response should contain "<expectedField>"

    Examples:
      | endpoint                          | statusCode | expectedField |
      | https://reqres.in/api/users?page=2 | 200        | page          |
      | https://reqres.in/api/users/2      | 200        | data          |
      | https://reqres.in/api/unknown/2    | 200        | data          |
