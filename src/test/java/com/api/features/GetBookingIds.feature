Feature: Retrieve Booking IDs from Restful Booker API with Authentication

  As a QA engineer
  I want to validate the GetBookingIds endpoint
  So that I can ensure it returns correct booking IDs with and without filters

  Background:
    Given I prepare the Booking API using auth type "<authType>"

  @Smoke @API
  Scenario Outline: Retrieve all booking IDs without filters
    When I fetch booking IDs without any search filters
    Then the system should reply with status <statusCode>
    And the payload should include a non-empty list of booking IDs

    Examples:
      | authType | statusCode |
      | BASIC    | 200        |
      | BEARER   | 200        |

  @Regression @API
  Scenario Outline: Retrieve booking IDs filtered by query parameters
    When I fetch booking IDs using filters:
      | key       | value       |
      | firstname | <firstname> |
      | lastname  | <lastname>  |
      | checkin   | <checkin>   |
      | checkout  | <checkout>  |
    Then the system should reply with status <statusCode>
    And the payload should include booking IDs that match the filters

    Examples:
      | authType | firstname | lastname | checkin    | checkout   | statusCode |
      | BASIC    | Sally     | Brown    | 2023-01-01 | 2023-01-10 | 200        |
      | BEARER   | Jim       | Smith    | 2023-02-15 | 2023-02-20 | 200        |
