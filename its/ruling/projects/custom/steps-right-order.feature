Feature: Purchase a product

  Scenario: Purchase a product
    Given I am a customer
    When I add a product to my cart
    Then I should see the product in my cart
    # Noncompliant [[sc=5;ec=9]] {{Unexpected When step. Reorder the steps of this scenario.}}
    When I proceed to the order payment
    Then I should see the order total amount

  Scenario Outline: Purchase a product
    Given I am a customer
    # Noncompliant [[sc=5;ec=9]] {{Unexpected Then step. Reorder the steps of this scenario.}}
    Then I should see the product in my cart
    And I should see the order total amount
    Examples:

  Scenario: Add a product to my cart
    Given I am a customer
    When I add a product to my cart
    Then I should see the product in my cart

  Scenario: Proceed to payment
    Given I am a customer
    And I have added a product to my cart
    When I proceed to payment
    Then I should see the order total amount

  Scenario: Proceed to payment
    And I am a customer
    And I have added a product to my cart
    Then I proceed to payment
    When I should see the order total amount
