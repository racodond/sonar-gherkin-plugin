 # Noncompliant {{Indent this line at column 0 (currently indented at column 1).}}
 @tag
 # Noncompliant {{Indent this line at column 0 (currently indented at column 1).}}
 Feature: My feature indentation custom KO
    Blabla...
    # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Blabla...

     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     Background: Blabla background indentation custom KO
        Blabla...
       # Noncompliant {{Indent this line at column 8 (currently indented at column 7).}}
       Blabla...
          # Noncompliant {{Indent this line at column 8 (currently indented at column 10).}}
          Given Blabla given1...

     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     @abc
   # Noncompliant {{Indent this line at column 4 (currently indented at column 3).}}
   Scenario: Scenario 1 indentation custom KO
         # Noncompliant {{Indent this line at column 8 (currently indented at column 9).}}
         Blabla...
        Blabla...
        Given Blabla given...
        When Blabla when...
        Then Blabla then...

    @def
     # Noncompliant {{Indent this line at column 4 (currently indented at column 5).}}
     @ghi @jkl
   # Noncompliant {{Indent this line at column 4 (currently indented at column 3).}}
   Scenario Outline: Scenario 2 indentation custom KO
       # Noncompliant {{Indent this line at column 8 (currently indented at column 7).}}
       Blabla...
        Blabla...
        Given Blabla given...
        When Blabla when...
            | data |
            # Noncompliant {{Indent this line at column 12 (currently indented at column 13).}}
             | 2    |
        Then Blabla then...<data>
             # Noncompliant {{Indent this line at column 12 (currently indented at column 13).}}
             """string
            blabla...
              blabla...
           """
           # Noncompliant [[sl=-1]] {{Indent this line at column 12 (currently indented at column 11).}}

         # Noncompliant {{Indent this line at column 8 (currently indented at column 9).}}
         @lmn @opq
         # Noncompliant {{Indent this line at column 8 (currently indented at column 9).}}
         Examples: Blabla examples indentation custom KO
             # Noncompliant {{Indent this line at column 12 (currently indented at column 13).}}
             Blabla...
            Blabla...
           # Noncompliant {{Indent this line at column 12 (currently indented at column 11).}}
           | data |
             # Noncompliant {{Indent this line at column 12 (currently indented at column 13).}}
             | 1    |
            | 2    |
