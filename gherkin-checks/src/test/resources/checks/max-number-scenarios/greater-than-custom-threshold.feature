# Noncompliant [[sc=1;ec=8]] {{The number of scenarios (5) is greater that the maximum allowed (4). Split the scenarios into different features.}}
Feature: My feature...

  Background:
    Given blabla...

  Scenario: scenario #1
    Given blabla...

  Scenario Outline: scenario #2
    Given blabla...
    Examples:
      | data |
      | 1    |
      | 2    |

  Scenario: scenario #3
    Given blabla...

  Scenario: scenario #4
    Given blabla...

  Scenario: scenario #5
    Given blabla...
