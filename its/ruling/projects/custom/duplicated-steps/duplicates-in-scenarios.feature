Feature: My feature Duplicates in scenarios
  Blabla...

  Background:
    Given Blabla1
    And Blabla2

  Scenario: My scenario 1 Duplicates in scenarios
    # Noncompliant [[sc=5;ec=18;secondary=-5]] {{Remove this duplicated step.}}
    Given Blabla1
    When Blabla when...2
    Then Blabla then...3

  Scenario Outline: My scenario 2 Duplicates in scenarios
    Given Blabla given...1
    # Noncompliant [[sc=5;ec=25;secondary=+1]] {{Remove this duplicated step.}}
    When Blabla when...2
    And Blabla when...2
    Then Blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
