Feature: Create multiple users on Reqres.in

  @ReqresTests
  Scenario: Create multiple users with DataTable
    Given I set the POST endpoint for creating users
    When I send POST requests for multiple users
      | name       | job                     |
      | Nagaraja   | Test Automation Engineer|
      | John Doe   | Developer               |
      | Alice      | QA Engineer             |
      | Bob        | DevOps Engineer         |
      | Clara      | Product Manager         |
      | David      | UX Designer             |
      | Emma       | Business Analyst        |
      | Frank      | Scrum Master            |
    Then each user should be created successfully with status code 201
