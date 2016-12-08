# Noncompliant {{Indent this line at column 0 (currently indented at column 1).}}
 @tag
@abc
  # Noncompliant {{Indent this line at column 0 (currently indented at column 2).}}
  Feature: My feature indentation default ko
    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    blabla...
  blabla...

 # Noncompliant {{Indent this line at column 2 (currently indented at column 1).}}
 Background: blabla background indentation default ko
  # Noncompliant {{Indent this line at column 4 (currently indented at column 2).}}
  blabla...
    blabla...
    # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Given blabla given1...

    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    @def
  # Noncompliant {{Indent this line at column 2 (currently indented at column 3).}}
   Scenario: Scenario 1 indentation default ko
    blabla...
    # Noncompliant {{Indent this line at column 4 (currently indented at column 7).}}
       blabla...
    Given blabla given...
    When blabla when...
    Then blabla then...

  @ghi
    # Noncompliant {{Indent this line at column 2 (currently indented at column 4).}}
    @jkl
   # Noncompliant {{Indent this line at column 2 (currently indented at column 3).}}
   Scenario Outline: Scenario 2 indentation default ko
    # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     blabla...
    blabla...
    Given blabla given...
    When blabla when...
      | data |
       # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       | 2    |
    Then blabla then...<data>
      # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       """string
      blabla...
        blabla...
     """
    # Noncompliant [[sl=-1]] {{Indent this line at column 6 (currently indented at column 5).}}

     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     @mno
     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Examples: blabla examples indentation default ko
      blabla...
      # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       blabla...
     # Noncompliant {{Indent this line at column 6 (currently indented at column 5).}}
     | data |
       # Noncompliant {{Indent this line at column 6 (currently indented at column 7).}}
       | 1    |
      | 2    |
