# Noncompliant {{Indent this line at column 0 (currently indented at column 1).}}
 @tag
@abc
  # Noncompliant {{Indent this line at column 0 (currently indented at column 2).}}
  Feature: My feature indentation default KO
    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    Blabla...
  Blabla...

 # Noncompliant {{Indent this line at column 2 (currently indented at column 1).}}
 Background: Blabla background indentation default KO
  # Noncompliant {{Indent this line at column 4 (currently indented at column 2).}}
  Blabla...
    Blabla...
    # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Given Blabla given1...

    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    @def
  # Noncompliant {{Indent this line at column 2 (currently indented at column 3).}}
   Scenario: Scenario 1 indentation default KO
    Blabla...
    # Noncompliant {{Indent this line at column 4 (currently indented at column 7).}}
       Blabla...
    Given Blabla given...
    When Blabla when...
    Then Blabla then...

  @ghi
    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    @jkl
   # Noncompliant {{Indent this line at column 2 (currently indented at column 3).}}
   Scenario Outline: Scenario 2 indentation default KO
    # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Blabla...
    Blabla...
    Given Blabla given...
    When Blabla when...
      | data |
       # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       | 2    |
    Then Blabla then...<data>
      # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       """string
      Blabla...
        Blabla...
     """
    # Noncompliant [[sl=-1]] {{Indent this line at column 6 (currently indented at column 5).}}

     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     @mno
     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Examples: Blabla examples indentation default KO
      Blabla...
      # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       Blabla...
     # Noncompliant {{Indent this line at column 6 (currently indented at column 5).}}
     | data |
       # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       | 1    |
      | 2    |
