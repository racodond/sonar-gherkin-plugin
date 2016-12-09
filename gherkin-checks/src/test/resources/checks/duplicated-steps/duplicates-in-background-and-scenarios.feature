Feature: My feature Duplicates in Background and scenarios
  Blabla...

  Background:
    # Noncompliant [[sc=5;ec=18;secondary=+2]] {{Remove this duplicated step.}}
    Given Blabla1
    And Blabla2
    And Blabla1

  Scenario: My scenario 1 Duplicates in Background and scenarios
    # Noncompliant [[sc=5;ec=18;secondary=-6,-4]] {{Remove this duplicated step.}}
    Given Blabla1
    When Blabla when...2
    Then Blabla then...3

  Scenario Outline: My scenario 2 Duplicates in Background and scenarios
    Given Blabla given...1
    # Noncompliant [[sc=5;ec=25;secondary=+1]] {{Remove this duplicated step.}}
    When Blabla when...2
    And Blabla when...2
    Then Blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
