# Noncompliant [[sl=+2;sc=1;ec=8;secondary=5,11,27]] {{Add tag "mytag2" to this feature and remove it from all scenarios.}}
@mytag
Feature: My feature Tag right level

  @mytag @mytag2 @abc
  Scenario: Scenario 1 Tag right level
    Given blabla...
    When blabla...
    Then blabla...

  @mytag @mytag2
  Scenario Outline: Scenario 2 Tag right level
    Given blabla...
    When blabla...
    Then blabla...<number>
    Examples:
      | number |
      | 1      |
      | 2      |

  @abc
  Scenario Outline: Scenario 3 Tag right level
    Given blabla...
    When blabla...
    Then blabla...<number>

    @mytag @mytag2
    Examples:
      | number |
      | 1      |
      | 2      |
