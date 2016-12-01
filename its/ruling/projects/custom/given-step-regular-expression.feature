Feature: my feature

  Scenario: Scenario 1
    # Noncompliant [[sc=11;ec=26]] {{Update the sentence to match the following regular expression: ^I .*$}}
    Given Page is catalog
    And I am a customer
    # Noncompliant [[sc=9;ec=12]] {{Update the sentence to match the following regular expression: ^I .*$}}
    But IOC
