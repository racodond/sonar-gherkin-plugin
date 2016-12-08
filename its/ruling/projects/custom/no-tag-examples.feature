Feature: My feature No tag Examples
  Blabla...

  Scenario Outline: Scenario 1 No tag Examples
    Given blabla given...
    When blabla when...
    Then blabla then... <number>

    # Noncompliant [[sl=+2;sc=5;ec=13;secondary=-1,-1]] {{Move these tags up to the Scenario Outline.}}
    @tag1 @tag2
    Examples:
      | number |
      | 1      |
      | 2      |


  @tag1 @tag2
  Scenario Outline: Scenario 2 No tag Examples
    Given blabla given...
    When blabla when...
    Then blabla then... <number>

    Examples:
      | number |
      | 1      |
      | 2      |
