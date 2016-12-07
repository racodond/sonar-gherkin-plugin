Feature: My feature Steps right order

  Scenario: Scenario 1 Steps right order
    Given I am a customer
    When I add a product to my cart
    Then I should see the product in my cart
    # Noncompliant [[sc=5;ec=9]] {{Unexpected When step. Reorder the steps of this scenario.}}
    When I proceed to the order payment
    Then I should see the order total amount

  Scenario Outline: Scenario 2 Steps right order
    Given I am a customer
    # Noncompliant [[sc=5;ec=9]] {{Unexpected Then step. Reorder the steps of this scenario.}}
    Then I should see the product in my cart
    And I should see the order total amount <data>
    Examples:
      | data |
      | 1    |
      | 2    |

  Scenario: Scenario 3 Steps right order
    Given I am a customer
    When I add a product to my cart
    Then I should see the product in my cart

  Scenario: Scenario 4 Steps right order
    Given I am a customer
    And I have added a product to my cart
    When I proceed to payment
    Then I should see the order total amount

  Scenario: Scenario 5 Steps right order
    And I am a customer
    And I have added a product to my cart
    Then I proceed to payment
    When I should see the order total amount
