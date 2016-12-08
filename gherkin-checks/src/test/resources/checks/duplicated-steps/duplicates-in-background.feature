Feature: My feature Duplicates in Background
  Blabla...

  Background:
    # Noncompliant [[sc=5;ec=18;secondary=+2]] {{Remove this duplicated step.}}
    Given blabla1
    And blabla2
    And blabla1

  Scenario: My scenario 1 Duplicates in Background
    Given blabla given...1
    When blabla when...2
    Then blabla then...3

  Scenario Outline: My scenario 2 Duplicates in Background
    Given blabla given...1
    When blabla when...2
    Then blabla then...3 <data>
    Examples:
      | data |
      | 1    |
      | 2    |
