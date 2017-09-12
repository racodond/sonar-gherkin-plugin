Feature: My feature Unused variable
  Blabla...

  Scenario Outline: Scenario 1 Unused variable
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 2 Unused variable
    Given Blabla given...
    When Blabla when...
    Then Blabla then...
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variable: number}}
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 3 Unused variable
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then... <number>
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variables: status, type}}
    Examples:
      | number | type | status |
      | 1      | book | open   |
      | 2      | bike | closed |

  Scenario Outline: Scenario 4 Unused variable
    Given Blabla given...:
      | abc | blabla<number> |
    When Blabla when...
    Then Blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 5 Unused variable
    Given Blabla given...:
      | abc | <number> |
    When Blabla when...
    Then Blabla then...
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variable: type}}
    Examples:
      | number | type |
      | 1      | bike |
      | 2      | book |

  Scenario Outline: Scenario 6 Unused variable
    Given Blabla given...:
      | abc | <number> |
    When Blabla when...<type>
    Then Blabla then...
    Examples:
      | number | type |
      | 1      | bike |
      | 2      | book |

  Scenario Outline: Scenario 7 Unused variable
    Given Blabla given...
    When Blabla when... <number>
    Then Blabla then...
    Examples:
      | number |
      | 1      |
      | 2      |
    Examples:
      | number |
      | 1      |
      | 2      |

  Scenario Outline: Scenario 5 Unused variable
    Given Blabla given...:
      | abc | <number> |
    When Blabla when...
    Then Blabla then...
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variable: type}}
    Examples:
      | number | type |
      | 1      | bike |
      | 2      | book |
    # Noncompliant [[sc=5;ec=13]] {{Remove the following unused variable: bbbb}}
    Examples:
      | number | bbbb |
      | 1      | bike |
      | 2      | book |
    Examples:
      | number |
      | 1      |
      | 2      |
