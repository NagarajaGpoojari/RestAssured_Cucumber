Feature: Authentication Token Creation

  @RestfulBooker
  Scenario Outline: Generate auth token with different credentials
    Given I authenticate using "<authType>" with username "<username>" and password "<password>"
    When I send a POST request to create an auth token at "<authUrl>"
    Then the auth response status should be <expectedStatus>
    And the auth response should contain "<expectedField>"

    Examples:
      | authType | username | password     | authUrl                                | expectedStatus | expectedField |
      | BASIC    | admin    | password123  | https://restful-booker.herokuapp.com/auth | 200            | token         |
      | BASIC    | admin    | wrongpass    | https://restful-booker.herokuapp.com/auth | 403            | error         |
      | BASIC    | wrong    | password123  | https://restful-booker.herokuapp.com/auth | 403            | error         |
      | BASIC    |          |              | https://restful-booker.herokuapp.com/auth | 403            | error         |
