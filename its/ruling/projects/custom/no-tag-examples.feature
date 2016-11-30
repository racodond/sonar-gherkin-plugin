Feature: my feature

  Scenario Outline: Scenario 1
    Given blabla...
    When blabla...
    Then blabla...

    # Noncompliant [[sl=+2;sc=5;ec=13;secondary=-1,-1]] {{Move these tags up to the Scenario Outline.}}
    @tag1 @tag2
    Examples:
      | number |
      | 1      |
      | 2      |


  @tag1 @tag2
  Scenario Outline:
    Given blabla...
    When blabla...
    Then blabla...

    Examples:
      | number |
      | 1      |
      | 2      |