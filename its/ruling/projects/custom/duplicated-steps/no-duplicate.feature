Feature: My feature No duplicated steps
  Blabla...

  Background:
    Given Blabla1
    And Blabla2

  Scenario: My scenario 1 No duplicated steps
    Given Blabla given...1
    When Blabla when...2
    Then Blabla then...3

  Scenario Outline: My scenario 2 No duplicated steps
    Given Blabla given...1
    When Blabla when...2
    Then Blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
