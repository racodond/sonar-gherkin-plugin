Feature: My feature

  Scenario: Scenario 1
    Given blabla...
    When blabla...
    Then blabla...

  Scenario Outline: Scenario 2
    Given blabla...
    When blabla...
    Then blabla...
    Examples:
      | number |
      | 1      |
      | 2      |

  # Noncompliant [[sc=3;ec=11]] {{Add at least one Given step.}}
  Scenario: Scenario 3
    When blabla...
    Then blabla...

  # Noncompliant [[sc=3;ec=11]] {{Add at least one When step.}}
  Scenario: Scenario 4
    Given blabla...
    Then blabla...

  # Noncompliant [[sc=3;ec=11]] {{Add at least one Then step.}}
  Scenario: Scenario 5
    Given blabla...
    When blabla...

  # Noncompliant [[sc=3;ec=19]] {{Add at least one Then step.}}
  Scenario Outline:
    Given blabla...
    When blabla...
    Examples:
      | number |
      | 1      |
      | 2      |