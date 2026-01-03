Feature: Validate API response with database integration

  Background:
    Given I establish a connection to the database

  @DatabaseValidation @API
  Scenario Outline: Cross-check user details between API and database
    When I retrieve user details from API for ID <userId> using auth type "<authType>"
    Then I cross-verify the API response with database records for ID <userId>

    Examples:
      | authType | userId |
      | BASIC    | 1      |
      | BEARER   | 2      |
      | BASIC    | 3      |
