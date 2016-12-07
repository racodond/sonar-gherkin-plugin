Feature: My feature only given steps to add to background...

  # Noncompliant [[sc=3;ec=13;secondary=8,9,14,15]] {{Add all common Given steps to Background.}}
  Background:
    Given aaa aaa aaa

  Scenario: Scenario 1 only given steps to add to background
    Given abc abc abc
    And def
    When blabla...
    Then blabla...

  Scenario: Scenario 2 only given steps to add to background
    Given abc abc abc
    And def
    When blabla...
    Then blabla...
