 # Noncompliant {{Indent this token at column 1 (currently indented at column 2).}}
 @tag
 # Noncompliant {{Indent this token at column 1 (currently indented at column 2).}}
 Feature: My feature indentation custom KO
    Blabla...
    # Noncompliant {{Indent this token at column 5 (currently indented at column 6).}}
     Blabla...

     # Noncompliant {{Indent this token at column 5 (currently indented at column 6).}}
     Background: Blabla background indentation custom KO
        Blabla...
       # Noncompliant {{Indent this token at column 9 (currently indented at column 8).}}
       Blabla...
          # Noncompliant {{Indent this token at column 9 (currently indented at column 11).}}
          Given Blabla given1...

     # Noncompliant {{Indent this token at column 5 (currently indented at column 6).}}
     @abc
   # Noncompliant {{Indent this token at column 5 (currently indented at column 4).}}
   Scenario: Scenario 1 indentation custom KO
         # Noncompliant {{Indent this token at column 9 (currently indented at column 10).}}
         Blabla...
        Blabla...
        Given Blabla given...
        When Blabla when...
        Then Blabla then...

    @def
     # Noncompliant {{Indent this token at column 5 (currently indented at column 6).}}
     @ghi @jkl
   # Noncompliant {{Indent this token at column 5 (currently indented at column 4).}}
   Scenario Outline: Scenario 2 indentation custom KO
       # Noncompliant {{Indent this token at column 9 (currently indented at column 8).}}
       Blabla...
        Blabla...
        Given Blabla given...
        When Blabla when...
            | data |
            # Noncompliant {{Indent this token at column 13 (currently indented at column 14).}}
             | 2    |
        Then Blabla then...<data>
             # Noncompliant {{Indent this token at column 13 (currently indented at column 14).}}
             """string
            blabla...
              blabla...
           """
           # Noncompliant [[sl=-1]] {{Indent this token at column 13 (currently indented at column 12).}}

         # Noncompliant {{Indent this token at column 9 (currently indented at column 10).}}
         @lmn @opq
         # Noncompliant {{Indent this token at column 9 (currently indented at column 10).}}
         Examples: Blabla examples indentation custom KO
             # Noncompliant {{Indent this token at column 13 (currently indented at column 14).}}
             Blabla...
            Blabla...
           # Noncompliant {{Indent this token at column 13 (currently indented at column 12).}}
           | data |
             # Noncompliant {{Indent this token at column 13 (currently indented at column 14).}}
             | 1    |
            | 2    |


    # Noncompliant [[sc=16;ec=50]] {{Indent this token at column 15 (currently indented at column 16).}}
    Scenario:  Scenario 3 - indentation custom KO
        Blabla...
        Given Blabla given...
        When Blabla when
        Then Blabla then...

    # Noncompliant [[sc=25;ec=60]] {{Indent this token at column 23 (currently indented at column 25).}}
    Scenario Outline:   Scenario 4 - indentation custom KO
        Blabla...
        Given Blabla given...
        When Blabla when...<data>
        Then Blabla then...

        # Noncompliant [[sc=20;ec=58]] {{Indent this token at column 19 (currently indented at column 20).}}
        Examples:  Blabla examples indentation custom KO
            | data |
            | 1    |
            | 2    |
