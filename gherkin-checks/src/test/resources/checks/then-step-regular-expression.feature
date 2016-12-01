Feature: my feature

  Scenario: Scenario 1
    # Noncompliant [[sc=10;ec=25]] {{Update the sentence to match the following regular expression: ^I should .*$}}
    Then Page is catalog
    And I should be a customer
    # Noncompliant [[sc=9;ec=16]] {{Update the sentence to match the following regular expression: ^I should .*$}}
    But Ishould
