Feature: My no spelling mistake feature is niice...
  This is the description of the feature.
  This is a spelling mistaike.

  Background: Scenario 1 - no spelling mistake - mistaike number
    Given My step sentence blabla toto

  Scenario: Scenario 2 - no spelling mistake - blabla car
    Given My given step sentence
    When My when step mistaike
    Then My then step sentence

  Scenario Outline: Scenario 3 - no spelling mistake - mistaike number
    Given My given step sentence
    When My when step sentence
    Then My then step sentence <test>
    Examples: example mistake
      | test |
      | 1    |
      | 2    |
