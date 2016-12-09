# Noncompliant [[sc=10;ec=40]] {{Rephrase this sentence to remove the following forbidden words: click, radio button}}
Feature: I click on the radio button...
  Blabla...

  # Noncompliant [[sc=21;ec=40]] {{Rephrase this sentence to remove the following forbidden words: radio button}}
  Scenario Outline: Blabla radio button
    Given Blabla given...
    # Noncompliant [[sc=10;ec=28]] {{Rephrase this sentence to remove the following forbidden words: fill in the form}}
    When I fill in the form
    Then Blabla then...<number>
    Examples:
      | number |
      | 1      |
      | 2      |

  # Noncompliant [[sc=13;ec=23]] {{Rephrase this sentence to remove the following forbidden words: click}}
  Scenario: I click on
    Given Blabla given...
    When Blabla when...
    Then Blabla then...
