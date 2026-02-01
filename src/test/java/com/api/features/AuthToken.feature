Feature: Authentication Token Creation
  This feature validates token generation with different credentials.

  @RestfulBooker
  Scenario Outline: Generate auth token with different credentials
    Given I authenticate with username "<username>" and password "<password>"
    When I send a POST request to create an auth token at "<authUrl>"
    Then the auth response status should be <expectedStatus>
    And the auth response should contain "<expectedField>"

    Examples:
      | username | password     | authUrl                                | expectedStatus | expectedField |
      | admin    | password123  | https://restful-booker.herokuapp.com/auth | 200            | token         |
      | admin    | wrongpass    | https://restful-booker.herokuapp.com/auth | 403            | error         |
      | wrong    | password123  | https://restful-booker.herokuapp.com/auth | 403            | error         |
      |          |              | https://restful-booker.herokuapp.com/auth | 403            | error         |
