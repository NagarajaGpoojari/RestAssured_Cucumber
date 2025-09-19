Feature: Validate API response with database

  Background:
    Given Database connection is established

  Scenario Outline: Validate user details from API with database
    Given I send a GET request for user <userId>
    Then I validate the response with database for user <userId>

    Examples:
      | userId |
      | 1      |
      | 2      |
      | 3      |
