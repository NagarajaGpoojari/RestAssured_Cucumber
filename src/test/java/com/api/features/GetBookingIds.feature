Feature: Retrieve Booking IDs from Restful Booker API

  As a QA engineer
  I want to validate the GetBookingIds endpoint
  So that I can ensure it returns correct booking IDs with and without filters

  Background:
    Given I initialize the Booking API

  @Smoke @API
  Scenario: Retrieve all booking IDs without filters
    When I send a GET request to booking endpoint without any query parameters
    Then the response status code should be 200
    And the response should contain a non-empty list of booking IDs

  @Regression @API
  Scenario Outline: Retrieve booking IDs filtered by query parameters
    When I send a GET request to booking endpoint with query parameters:
      | key       | value       |
      | firstname | <firstname> |
      | lastname  | <lastname>  |
      | checkin   | <checkin>   |
      | checkout  | <checkout>  |
    Then the response status code should be 200
    And the response should contain booking IDs matching the filter criteria

    Examples:
      | firstname | lastname | checkin    | checkout   |
      | Sally     | Brown    | 2023-01-01 | 2023-01-10 |
      | Jim       | Smith    | 2023-02-15 | 2023-02-20 |
