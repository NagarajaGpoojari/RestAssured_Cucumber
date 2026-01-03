Feature: Create multiple users on Reqres.in with Authentication

  @ReqresTests
  Scenario Outline: Create multiple users with DataTable
    Given I prepare the bulk user creation resource with auth type "<authType>"
    When I dispatch POST calls for each user entry
      | name       | job                     |
      | <name>     | <job>                   |
    Then the system should confirm each creation with status <statusCode>

    Examples:
      | authType | name      | job                     | statusCode |
      | BASIC    | Nagaraja  | Test Automation Engineer| 201        |
      | BASIC    | John Doe  | Developer               | 201        |
      | BEARER   | Alice     | QA Engineer             | 201        |
      | BEARER   | Bob       | DevOps Engineer         | 201        |
      | BASIC    | Clara     | Product Manager         | 201        |
      | BASIC    | David     | UX Designer             | 201        |
      | BEARER   | Emma      | Business Analyst        | 201        |
      | BEARER   | Frank     | Scrum Master            | 201        |
