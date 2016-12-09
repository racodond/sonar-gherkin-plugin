Feature: My feature Given step regular expression
  Blabla...

  Scenario: Scenario 1 Given step regular expression
    # Noncompliant [[sc=11;ec=26]] {{Update the sentence to match the following regular expression: ^I .*$}}
    Given Page is catalog
    And I am a customer
    # Noncompliant [[sc=9;ec=23]] {{Update the sentence to match the following regular expression: ^I .*$}}
    But Issue number 1
    When Blabla when...
    Then Blabla then...
