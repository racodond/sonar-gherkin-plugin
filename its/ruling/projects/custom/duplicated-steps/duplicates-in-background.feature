Feature: My feature Duplicates in Background
  Blabla...

  Background:
    # Noncompliant [[sc=5;ec=18;secondary=+2]] {{Remove this duplicated step.}}
    Given Blabla1
    And Blabla2
    And Blabla1

  Scenario: My scenario 1 Duplicates in Background
    Given Blabla given...1
    When Blabla when...2
    Then Blabla then...3

  Scenario Outline: My scenario 2 Duplicates in Background
    Given Blabla given...1
    When Blabla when...2
    Then Blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
