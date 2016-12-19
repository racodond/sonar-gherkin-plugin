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

import java.util.Set;
import java.util.stream.Collectors;

public enum GherkinLexicalGrammar implements GrammarRuleKey {
  GHERKIN_DOCUMENT,

  LANGUAGE,

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

  public static LexerlessGrammarBuilder createGrammar(String language) {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    syntax(b, language);
    b.setRootRule(GHERKIN_DOCUMENT);
    return b;
  }

  private static void syntax(LexerlessGrammarBuilder b, String language) {
    String whitespaceRegex = "(?<!\\\\)[\\s]*+";
    String whitespaceWithoutLineBreakRegex = "(?<!\\\\)[ \\t\\x0B\\f]*+";
    String whitespaceNotFollowedByLineBreakRegex = "(?<!\\\\)[ \\t\\x0B\\f]+(?!(\n|\r))";
    String trimmedSentence = "(\\S+|" + whitespaceNotFollowedByLineBreakRegex + ")+";

    GherkinDialect dialect = GherkinDialectProvider.getDialect(language);

    Set<String> featureKeywords = dialect.getFeatureKeywords();
    Set<String> backgroundKeywords = dialect.getBackgroundKeywords();
    Set<String> scenarioKeywords = dialect.getScenarioKeywords();
    Set<String> scenarioOutlineKeywords = dialect.getScenarioOutlineKeywords();
    Set<String> examplesKeywords = dialect.getExamplesKeywords();
    Set<String> stepKeywords = dialect.getStepKeywords();

    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
    b.rule(BOM).is("\ufeff");

    b.rule(TAG_PREFIX).is(SPACING, b.regexp("@"));
    b.rule(TAG_LITERAL).is(b.regexp("[^@\\s]+"));

    b.rule(FEATURE_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(featureKeywords)));
    b.rule(BACKGROUND_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(backgroundKeywords)));
    b.rule(SCENARIO_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(scenarioKeywords)));
    b.rule(SCENARIO_OUTLINE_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(scenarioOutlineKeywords)));
    b.rule(EXAMPLES_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(examplesKeywords)));
    b.rule(STEP_KEYWORD).is(SPACING, b.regexp(convertListToRegexPattern(stepKeywords)));

    b.rule(COLON).is(":");

    b.rule(NAME_LITERAL).is(WHITESPACE_WITHOUT_LINE_BREAK, b.regexp(trimmedSentence));

    b.rule(STEP_SENTENCE_LITERAL).is(b.regexp(trimmedSentence));

    b.rule(FEATURE_DESCRIPTION_SENTENCE).is(
      SPACING,
      b.regexp("^(?!("
        + convertListToRegexPattern(backgroundKeywords)
        + "|"
        + convertListToRegexPattern(scenarioKeywords)
        + "|"
        + convertListToRegexPattern(scenarioOutlineKeywords)
        + "|@))"
        + trimmedSentence));

    b.rule(SCENARIO_DESCRIPTION_SENTENCE).is(
      SPACING,
      b.regexp("^(?!("
        + convertListToRegexPattern(backgroundKeywords)
        + "|"
        + convertListToRegexPattern(scenarioKeywords)
        + "|"
        + convertListToRegexPattern(scenarioOutlineKeywords)
        + "|"
        + convertListToRegexPattern(stepKeywords)
        + "|"
        + convertListToRegexPattern(examplesKeywords)
        + "|@))"
        + trimmedSentence));

    b.rule(EXAMPLES_DESCRIPTION_SENTENCE).is(
      SPACING,
      b.regexp("^(?!("
        + convertListToRegexPattern(backgroundKeywords)
        + "|"
        + convertListToRegexPattern(scenarioKeywords)
        + "|"
        + convertListToRegexPattern(scenarioOutlineKeywords)
        + "|@|\\|))"
        + trimmedSentence));

    b.rule(DOC_STRING_PREFIX).is(SPACING, "\"\"\"");
    b.rule(DOC_STRING_SUFFIX).is(SPACING_NO_COMMENTS, "\"\"\"");
    b.rule(DOC_STRING_DATA_ROW).is(SPACING_NO_COMMENTS, b.regexp("^(?!\"\"\").+"));
    b.rule(DOC_STRING_CONTENT_TYPE).is(b.regexp(".+"));

    b.rule(TABLE_DATA_ROW).is(SPACING, b.regexp("\\|.*\\|"));

    b.rule(LANGUAGE).is(b.regexp(GherkinDialectProvider.LANGUAGE_DECLARATION_PATTERN.pattern()));

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

  private static String convertListToRegexPattern(Set<String> keywords) {
    return keywords
      .stream()
      .map(k -> "* ".equals(k) ? "\\* " : k)
      .collect(Collectors.joining("|"));
  }

}
