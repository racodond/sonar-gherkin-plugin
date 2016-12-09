# Noncompliant [[sl=+2;sc=1;ec=8;secondary=6,12,28]] {{Add tag "mytag2" to this feature and remove it from all scenarios.}}
@mytag
Feature: My feature Tag right level
  Blabla...

  @mytag @mytag2 @abc
  Scenario: Scenario 1 Tag right level
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  @mytag @mytag2
  Scenario Outline: Scenario 2 Tag right level
    Given Blabla given...
    When Blabla when...
    Then Blabla then...<number>
    Examples:
      | number |
      | 1      |
      | 2      |

  @abc
  Scenario Outline: Scenario 3 Tag right level
    Given Blabla given...
    When Blabla when...
    Then Blabla then...<number>

    @mytag @mytag2
    Examples:
      | number |
      | 1      |
      | 2      |
