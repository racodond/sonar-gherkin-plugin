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

import org.junit.Test;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;

import static org.fest.assertions.Assertions.assertThat;

public class StepSentenceTreeTest extends GherkinTreeTest {

  public StepSentenceTreeTest() {
    super(GherkinLexicalGrammar.STEP_SENTENCE);
  }

  @Test
  public void stepSentence() throws Exception {
    checkParsed("abc\n", "abc");
    checkParsed("abc \n", "abc");
    checkParsed("abc def\n", "abc def");
    checkParsed("abc\\\\n def\n", "abc\\\\n def");
    checkParsed("abc\\\\n def \n", "abc\\\\n def");
  }

  @Test
  public void notStepSentence() throws Exception {
    checkNotParsed("Given abc\n");
    checkNotParsed("When abc\n");
    checkNotParsed("Then abc\n");
    checkNotParsed("And abc\n");
    checkNotParsed("But abc\n");
    checkNotParsed("* abc\n");
  }

  private void checkParsed(String toParse, String expected) {
    SyntaxToken token = (SyntaxToken) parser().parse(toParse);
    assertThat(token).isNotNull();
    assertThat(token.text()).isEqualTo(expected);
  }

}
