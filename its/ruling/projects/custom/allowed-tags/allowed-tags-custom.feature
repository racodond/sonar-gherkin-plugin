# Noncompliant [[sc=1;ec=5]] {{Remove this tag that does not match the regular expression: "mytag|yourtag"}}
@nrt
Feature: My feature allowed tags custom...
  Blabla...

  # Noncompliant [[sc=3;ec=23]] {{Remove this tag that does not match the regular expression: "mytag|yourtag"}}
  @non-regression-test
  Scenario: Scenario 1 allowed tags custom
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  @mytag @yourtag
  Scenario: Scenario 2 allowed tags custom
    Given Blabla given...
    When Blabla when...
    Then Blabla then...
