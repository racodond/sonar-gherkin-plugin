Feature: My feature No duplicated steps
  Blabla...

  Background:
    Given blabla1
    And blabla2

  Scenario: My scenario 1 No duplicated steps
    Given blabla given...1
    When blabla when...2
    Then blabla then...3

  Scenario Outline: My scenario 2 No duplicated steps
    Given blabla given...1
    When blabla when...2
    Then blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
