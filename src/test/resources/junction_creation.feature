Feature: Junction Creation

  Scenario: Successfully create a new junction
    Given a junction of type "NOT_MANAGED" with no outgoing roads, located in 0, 0
    When is created
    Then should be saved correctly