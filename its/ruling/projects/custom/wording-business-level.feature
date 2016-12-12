# Noncompliant [[sl=+2]] {{Remove this forbidden word.}}
# Noncompliant {{Remove this forbidden word.}}
Feature: I click on the radio button...
  Blabla...

  # Noncompliant [[sc=28;ec=40]] {{Remove this forbidden word.}}
  Scenario Outline: Blabla radio button
    Given Blabla given...
    # Noncompliant [[sc=12;ec=28]] {{Remove this forbidden word.}}
    When I fill in the form
    Then Blabla then...<number>
    Examples:
      | number |
      | 1      |
      | 2      |

  # Noncompliant [[sc=15;ec=20]] {{Remove this forbidden word.}}
  Scenario: I click on
    Given Blabla given...
    When Blabla when...
    Then Blabla then...
