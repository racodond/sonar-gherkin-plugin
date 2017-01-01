/*
 * SonarQube Cucumber Gherkin Analyzer
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

import org.sonar.sslr.grammar.GrammarRuleKey;

import java.nio.charset.Charset;

public class GherkinParserBuilder {

  private GherkinParserBuilder() {
  }

  public static GherkinParser createParser(Charset charset, String language) {
    return createParser(charset, GherkinLexicalGrammar.GHERKIN_DOCUMENT, language);
  }

  public static GherkinParser createTestParser(Charset charset, GrammarRuleKey rootRule, String language) {
    return createParser(charset, rootRule, language);
  }

  public static GherkinParser createTestParser(Charset charset, GrammarRuleKey rootRule) {
    return createParser(charset, rootRule, GherkinDialectProvider.DEFAULT_LANGUAGE);
  }

  public static GherkinParser createTestParser(Charset charset, String language) {
    return createParser(charset, GherkinLexicalGrammar.GHERKIN_DOCUMENT, language);
  }

  public static GherkinParser createTestParser(Charset charset) {
    return createParser(charset, GherkinLexicalGrammar.GHERKIN_DOCUMENT, GherkinDialectProvider.DEFAULT_LANGUAGE);
  }

  private static GherkinParser createParser(Charset charset, GrammarRuleKey rootRule, String language) {
    return new GherkinParser(
      charset,
      GherkinLexicalGrammar.createGrammar(language),
      GherkinGrammar.class,
      new TreeFactory(),
      new GherkinNodeBuilder(),
      rootRule);
  }

}
