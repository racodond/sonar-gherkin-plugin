Feature: My feature Then step regular expression
  Blabla...

  Scenario: Scenario 1 Then step regular expression
    Given Blabla given...
    When Blabla when...
    # Noncompliant [[sc=10;ec=25]] {{Update the sentence to match the following regular expression: ^I should .*$}}
    Then Page is catalog
    And I should be a customer
    # Noncompliant [[sc=9;ec=15]] {{Update the sentence to match the following regular expression: ^I should .*$}}
    But Issues
