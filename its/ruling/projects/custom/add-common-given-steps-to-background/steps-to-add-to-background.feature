Feature: My feature steps to add to background...

  # Noncompliant [[sc=3;ec=13;secondary=8,9,14,15]] {{Add all common Given steps to Background.}}
  Background:
    Given aaa aaa aaa

  Scenario: Scenario 1 steps to add to background
    Given abc abc abc
    And def
    When blabla...
    Then blabla...

  Scenario Outline: Scenario 2 steps to add to background
    Given abc abc abc
    And def
    And hij
    When blabla...
    Then blabla...<data>
    Examples:
      | data |
      | 1    |
      | 2    |
