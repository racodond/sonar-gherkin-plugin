Feature: My feature Unused variable
  Blabla...

  Scenario Outline: Scenario 1 Unused variable
    Given blabla given...
    When blabla when... <number>
    Then blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2 Unused variable
    Given blabla given...
    When blabla when...
    Then blabla then...
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variable: number}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 3 Unused variable
    Given blabla given...
    When blabla when... <number>
    Then blabla then... <number>
  # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variables: status, type}}
    Examples:
      | number | type | status |
      | 1      | book | open   |
      | 2      | bike | closed |
