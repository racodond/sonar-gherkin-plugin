Feature: My feature Missing data column
  Blabla...

  Scenario Outline: Scenario 1 Missing data column
    Given blabla given...
    When blabla when... <number>
    Then blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2 Missing data column
    Given blabla given...
    When blabla when... <number>
    Then blabla then... <type>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table column: type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 3 Missing data column
    Given blabla given...
    When blabla when... <number>
    Then blabla then... <type> <status>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table columns: status, type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 4 Missing data column
    Given blabla given...
    When blabla when... <number> <status>
    Then blabla then... <status>
    # Noncompliant [[sc=5;ec=13]] {{Add a data table with the following missing columns: number, status}}
    Examples:
