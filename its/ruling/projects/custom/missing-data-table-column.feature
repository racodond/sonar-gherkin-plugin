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
    When blabla... <number>
    Then blabla... <type>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table column: type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 3
    Given blabla...
    When blabla... <number>
    Then blabla... <type> <status>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table columns: status, type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 4
    Given blabla...
    When blabla... <number> <status>
    Then blabla... <status>
    # Noncompliant [[sc=5;ec=13]] {{Add a data table with the following missing columns: number, status}}
    Examples:
