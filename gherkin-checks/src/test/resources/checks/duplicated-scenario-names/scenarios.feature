Feature: Feature duplicated scenario names
  Blabla...

  Background: background name blabla
    Given blabla...

  Scenario: scenario name blabla
    Given blabla...
    When blabla...
    Then blabla...

  Scenario: scenario name blabla
    Given blabla...
    When blabla...
    Then blabla...

  Scenario:
    Given blabla...
    When blabla...
    Then blabla...

  Scenario Outline: scenario outline name blabla
    Given blabla...<number>
    When blabla...
    Then blabla...

    Examples: examples name blabla
      | number |
      | 1      |
      | 2      |
