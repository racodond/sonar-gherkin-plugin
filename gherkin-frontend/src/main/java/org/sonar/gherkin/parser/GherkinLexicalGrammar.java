/*
 * SonarQube Gherkin Analyzer
 * Copyright (C) 2016-2016 David RACODON
 * david.racodon@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.gherkin.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum GherkinLexicalGrammar implements GrammarRuleKey {
  GHERKIN_DOCUMENT,

  FEATURE,
  FEATURE_DECLARATION,
  FEATURE_PREFIX,
  FEATURE_KEYWORD,

  BACKGROUND,
  BACKGROUND_PREFIX,
  BACKGROUND_KEYWORD,

  SCENARIO,
  SCENARIO_PREFIX,
  SCENARIO_KEYWORD,

  SCENARIO_OUTLINE,
  SCENARIO_OUTLINE_PREFIX,
  SCENARIO_OUTLINE_KEYWORD,

  EXAMPLES,
  EXAMPLES_PREFIX,
  EXAMPLES_KEYWORD,

  STEP,
  STEP_PREFIX,
  STEP_KEYWORD,
  STEP_SENTENCE,
  STEP_SENTENCE_LITERAL,

  COLON,

  NAME,
  NAME_LITERAL,

  FEATURE_DESCRIPTION,
  SCENARIO_DESCRIPTION,
  EXAMPLES_DESCRIPTION,
  FEATURE_DESCRIPTION_SENTENCE,
  SCENARIO_DESCRIPTION_SENTENCE,
  EXAMPLES_DESCRIPTION_SENTENCE,

  TAG,
  TAG_PREFIX,
  TAG_LITERAL,

  DOC_STRING,
  DOC_STRING_CONTENT_TYPE,
  DOC_STRING_PREFIX,
  DOC_STRING_SUFFIX,
  DOC_STRING_DATA_ROW,

  TABLE,
  TABLE_DATA_ROW,

  SPACING,
  SPACING_NO_COMMENTS,
  WHITESPACE_WITHOUT_LINE_BREAK,

  BOM,
  EOF;

  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    syntax(b);
    b.setRootRule(GHERKIN_DOCUMENT);
    return b;
  }

  private static void syntax(LexerlessGrammarBuilder b) {

    String whitespaceRegex = "(?<!\\\\)[\\s]*+";
    String whitespaceWithoutLineBreakRegex = "(?<!\\\\)[ \\t\\x0B\\f]*+";
    String whitespaceNotFollowedByLineBreakRegex = "(?<!\\\\)[ \\t\\x0B\\f]+(?!(\n|\r))";
    String trimmedSentence = "(\\S+|" + whitespaceNotFollowedByLineBreakRegex + ")+";

    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
    b.rule(BOM).is("\ufeff");

    b.rule(TAG_PREFIX).is(SPACING, b.regexp("@"));
    b.rule(TAG_LITERAL).is(b.regexp("[^@\\s]+"));

    b.rule(FEATURE_KEYWORD).is(SPACING, "Feature");
    b.rule(BACKGROUND_KEYWORD).is(SPACING, "Background");
    b.rule(SCENARIO_KEYWORD).is(SPACING, "Scenario");
    b.rule(SCENARIO_OUTLINE_KEYWORD).is(SPACING, "Scenario Outline");
    b.rule(EXAMPLES_KEYWORD).is(SPACING, "Examples");
    b.rule(STEP_KEYWORD).is(SPACING, b.firstOf("Given", "When", "Then", "And", "But", "*"));

    b.rule(COLON).is(":");

    b.rule(NAME_LITERAL).is(WHITESPACE_WITHOUT_LINE_BREAK, b.regexp(trimmedSentence));
    b.rule(STEP_SENTENCE_LITERAL).is(SPACING, b.regexp("^(?!(Given|When|Then|And|But|\\*|Feature|Background|Scenario|Examples|@|\"\"\"|\\|))" + trimmedSentence));

    b.rule(FEATURE_DESCRIPTION_SENTENCE).is(SPACING, b.regexp("^(?!(Background|Scenario|@))" + trimmedSentence));
    b.rule(SCENARIO_DESCRIPTION_SENTENCE).is(SPACING, b.regexp("^(?!(Background|Scenario|Given|When|Then|And|But|\\*|Examples|@))" + trimmedSentence));
    b.rule(EXAMPLES_DESCRIPTION_SENTENCE).is(SPACING, b.regexp("^(?!(Background|Scenario|@|\\|))" + trimmedSentence));

    b.rule(DOC_STRING_PREFIX).is(SPACING, "\"\"\"");
    b.rule(DOC_STRING_SUFFIX).is(SPACING_NO_COMMENTS, "\"\"\"");
    b.rule(DOC_STRING_DATA_ROW).is(SPACING_NO_COMMENTS, b.regexp("^(?!\"\"\").+"));
    b.rule(DOC_STRING_CONTENT_TYPE).is(b.regexp(".+"));

    b.rule(TABLE_DATA_ROW).is(SPACING, b.regexp("\\|.*\\|"));

    b.rule(SPACING).is(
      b.skippedTrivia(b.regexp(whitespaceRegex)),
      b.zeroOrMore(
        b.commentTrivia(b.regexp("#.*")),
        b.skippedTrivia(b.regexp(whitespaceRegex))));

    b.rule(WHITESPACE_WITHOUT_LINE_BREAK).is(
      b.skippedTrivia(b.regexp(whitespaceWithoutLineBreakRegex)));

    b.rule(SPACING_NO_COMMENTS).is(
      b.skippedTrivia(b.regexp(whitespaceRegex)));
  }

}
