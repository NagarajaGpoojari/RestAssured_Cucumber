Feature: Authentication Token Creation

@RestfuleBooker
  Scenario Outline: Generate auth token with different credentials
    Given I have username "<username>" and password "<password>"
    When I send a POST request to create an auth token
    Then I should receive a <status> response

    Examples:
      | username | password     | status  |
      | admin    | password123  | success |
      | admin    | wrongpass    | failure |
      | wrong    | password123  | failure |
      |          |              | failure |
