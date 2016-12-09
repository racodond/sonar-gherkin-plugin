Feature: My feature Missing data column
  Blabla...

  Scenario Outline: Scenario 1 Missing data column
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2 Missing data column
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then... <type>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table column: type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 3 Missing data column
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then... <type> <status>
    # Noncompliant [[sc=5;ec=13]] {{Add the following missing data table columns: status, type}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 4 Missing data column
    Given Blabla given...
    When Blabla when... <number> <status>
    Then Blabla then... <status>
    # Noncompliant [[sc=5;ec=13]] {{Add a data table with the following missing columns: number, status}}
    Examples:
