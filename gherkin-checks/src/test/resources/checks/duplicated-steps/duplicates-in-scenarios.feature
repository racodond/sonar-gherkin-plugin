Feature: My feature Duplicates in scenarios
  Blabla...

  Background:
    Given blabla1
    And blabla2

  Scenario: My scenario 1 Duplicates in scenarios
    # Noncompliant [[sc=5;ec=18;secondary=-5]] {{Remove this duplicated step.}}
    Given blabla1
    When blabla when...2
    Then blabla then...3

  Scenario Outline: My scenario 2 Duplicates in scenarios
    Given blabla given...1
    # Noncompliant [[sc=5;ec=25;secondary=+1]] {{Remove this duplicated step.}}
    When blabla when...2
    And blabla when...2
    Then blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
