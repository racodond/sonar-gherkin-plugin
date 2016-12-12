# Noncompliant [[sc=24;ec=29]] {{Possible spelling mistake found. Suggested correction(s): [nice, niece, Nice].}}
Feature: My feature is niice...
  # Noncompliant [[sl=+2;sc=22;ec=30]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  This is the description of the feature.
  This is a spelling mistaike.

  # Noncompliant [[sc=24;ec=32]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  Background: Scenario mistaike number 2
    Given My step sentence blabla toto

  Scenario: Scenario 2 - blabla car
    Given My given step sentence
    # Noncompliant [[sc=23;ec=31]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
    When My when step mistaike
    Then My then step sentence

  # Noncompliant [[sc=30;ec=38]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
  Scenario Outline: Scenario mistaike number 3
    Given My given step sentence
    When My when step sentence
    Then My then step sentence <test>
    # Noncompliant [[sc=23;ec=31]] {{Possible spelling mistake found. Suggested correction(s): [mistake].}}
    Examples: Example mistaike
      | test |
      | 1    |
      | 2    |
