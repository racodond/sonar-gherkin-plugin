Feature: My feature Step of unknown type

  Scenario: Scenario 1 Step of unknown type
    # Noncompliant [[sc=5;ec=8]] {{Update the prefix of this unknown type step.}}
    And I am a customer
    When I add a product to my cart
    Then I should see the product in my cart
    And I should see the update total amount

  Scenario: Scenario 1 Step of unknown type
    Given I am a customer
    When I add a product to my cart
    Then I should see the product in my cart
    And I should see the update total amount
