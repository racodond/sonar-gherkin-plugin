# Noncompliant [[sc=10;ec=32]] {{Possible spelling mistake found. Suggested correction(s): [nice, niece, Nice].}}
Feature: My feature is niice...
  # Noncompliant [[sc=3;ec=31]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  This is the description of the feature.
  This is a spelling mistaike.

  # Noncompliant [[sc=15;ec=41]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  Background: Scenario mistaike number 2
    Given My step sentence blabla toto

  Scenario: Scenario 2 - blabla car
    Given My given step sentence
    # Noncompliant [[sc=10;ec=31]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
    When My when step mistaike
    Then My then step sentence

  # Noncompliant [[sc=21;ec=47]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  Scenario Outline: Scenario mistaike number 3
    Given My given step sentence
    When My when step sentence
    Then My then step sentence <test>
    # Noncompliant [[sc=15;ec=31]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
    Examples: Example mistaike
      | test |
      | 1    |
      | 2    |
