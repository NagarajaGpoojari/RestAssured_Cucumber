Feature: Create multiple users on Reqres.in with Authentication
  This feature validates bulk user creation with different user data.

  @ReqresTests
  Scenario Outline: Create multiple users with DataTable
    Given I prepare the bulk user creation resource
    When I dispatch POST calls for each user entry
      | name   | job   |
      | <name> | <job> |
    Then the system should confirm each creation with status <statusCode>

    Examples:
      | name      | job                     | statusCode |
      | Nagaraja  | Test Automation Engineer| 201        |
      | John Doe  | Developer               | 201        |
      | Alice     | QA Engineer             | 201        |
      | Bob       | DevOps Engineer         | 201        |
      | Clara     | Product Manager         | 201        |
      | David     | UX Designer             | 201        |
      | Emma      | Business Analyst        | 201        |
      | Frank     | Scrum Master            | 201        |
