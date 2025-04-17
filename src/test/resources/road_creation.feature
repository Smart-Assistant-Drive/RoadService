Feature: Road Creation

  Scenario: Successfully create a new road
    Given the road described as "road1", "123" and 0
    Given is updated with "road2", "321" and 0
    Then it should be updated correctly