Feature: My feature max number steps custom threshold...
  Blabla...

  Scenario: scenario #1 max number steps custom threshold
    Given blabla given1...
    When blabla when...
    Then blabla then...
    And blabla t1...
    And blabla t2...

  # Noncompliant [[sc=3;ec=11]] {{Reduce the number of steps (7, greater than 5 allowed).}}
  Scenario: scenario #2 max number steps custom threshold
    Given blabla given...
    When blabla when...
    Then blabla then...
    And blabla t1...
    And blabla t2...
    And blabla t3...
    And blabla t4...
