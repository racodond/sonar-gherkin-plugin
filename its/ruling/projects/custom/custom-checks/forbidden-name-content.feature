# Noncompliant [[sc=10;ec=27]] {{Remove this usage of "WTF".}}
Feature: WTF My feature...
  Blabla...

  Scenario: Scenario #1 forbidden name
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  # Noncompliant [[sc=13;ec=28]] {{Remove this usage of "WTF".}}
  Scenario: Scenario WTF #2
    Given Blabla given...
    When Blabla when...
    Then Blabla then...
