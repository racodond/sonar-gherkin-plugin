@mytag
Feature: My feature Useless tag
  Blabla...

  # Noncompliant [[sc=13;ec=19;secondary=1]] {{Remove this useless tag that is already set at feature level.}}
  @othertag @mytag
  Scenario: My scenario 1 Useless tag
    Given blabla...
    When blabla...
    Then blabla...

  # Noncompliant [[sc=3;ec=9;secondary=1]] {{Remove this useless tag that is already set at feature level.}}
  @mytag @abc
  Scenario Outline: Scenario 2 Useless tag
    Given blabla...
    When blabla...
    Then blabla...<number>

    # Noncompliant [[sc=5;ec=11;secondary=1]] {{Remove this useless tag that is already set at feature level.}}
    @mytag @def
    Examples:
      | number |
      | 1      |
      | 2      |

  @othertag
  Scenario: My scenario 3 Useless tag
    Given blabla...
    When blabla...
    Then blabla...

  @abc
  Scenario Outline: Scenario 4 Useless tag
    Given blabla...
    When blabla...
    Then blabla...<number>

    @def
    Examples:
      | number |
      | 1      |
      | 2      |
