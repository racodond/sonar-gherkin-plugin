Feature: My feature

  # Noncompliant [[sc=3;ec=13]] {{Remove this "Background" that does not define any step.}}
  Background:

  # Noncompliant [[sc=3;ec=11]] {{Remove this "Scenario" that does not define any step.}}
  Scenario: Scenario 1

  # Noncompliant [[sc=3;ec=19]] {{Remove this "Scenario Outline" that does not define any step.}}
  Scenario Outline: Scenario 2
    Examples:
      | number |
      | 1      |
      | 2      |
