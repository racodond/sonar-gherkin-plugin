Feature: My feature Incomplete Examples table
  Blabla...

  Scenario Outline: Scenario 1 Incomplete Examples table
    Given blabla given...
    When blabla when...
    Then blabla then... <test>
    # Noncompliant [[sc=5;ec=13]] {{Add a data table to this Examples section.}}
    Examples:

  Scenario Outline: Scenario 2 Incomplete Examples table
    Given blabla given...
    When blabla when...
    Then blabla then... <test>
    Examples:
      # Noncompliant [[sc=7;ec=15]] {{Add data rows to this data table.}}
      | test |

  Scenario Outline: Scenario 3 Incomplete Examples table
    Given blabla given...
    When blabla when...
    Then blabla then... <test>
    Examples:
      # Noncompliant [[sc=7;ec=15]] {{Add data rows to this data table or convert this Scenario Outline to a standard Scenario.}}
      | test |
      | 1    |

  Scenario Outline: Scenario 4 Incomplete Examples table
    Given blabla given...
    When blabla when...
    Then blabla then... <test>
    Examples:
      | test |
      | 1    |
      | 2    |
