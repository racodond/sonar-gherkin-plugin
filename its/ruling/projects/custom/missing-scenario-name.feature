Feature: My feature Missing Scenario name
  Blabla...

  Scenario: Scenario 1 - Missing Scenario name
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  Scenario Outline: Scenario 2 - Missing Scenario name
    Given Blabla given...
    When Blabla when...
    Then Blabla then... <number>
    Examples:
      | number |
      | 1      |
      | 2      |

  # Noncompliant [[sc=3;ec=11]] {{Add a name to this scenario.}}
  Scenario:
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  # Noncompliant [[sc=3;ec=19]] {{Add a name to this scenario.}}
  Scenario Outline:
    Given Blabla given...
    When Blabla when...
    Then Blabla then... <number>
    Examples:
      | number |
      | 1      |
      | 2      |
