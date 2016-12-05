Feature: My feature

  Scenario Outline: Scenario 1
    Given blabla...
    When blabla... <number>
    Then blabla...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2
    Given blabla...
    When blabla...
    Then blabla...
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variables: number}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2
    Given blabla...
    When blabla... <number>
    Then blabla...
  # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variables: status, type}}
    Examples:
      | number | type | status |
      | 1      | book | open   |
      | 2      | bike | closed |
