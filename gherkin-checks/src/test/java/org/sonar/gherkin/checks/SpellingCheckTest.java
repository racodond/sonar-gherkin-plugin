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
package org.sonar.gherkin.checks;

import org.junit.Test;
import org.sonar.gherkin.checks.verifier.GherkinCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class SpellingCheckTest {

  @Test
  public void should_find_some_spelling_mistakes_and_raise_some_issues() {
    SpellingCheck check = new SpellingCheck();
    check.setWordsToIgnore("blabla,toto");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("spelling/spelling.feature"));
  }

  @Test
  public void should_not_find_any_spelling_mistake_because_some_spelling_rules_are_exclude() {
    SpellingCheck check = new SpellingCheck();
    check.setWordsToIgnore("blabla,toto");
    check.setRulesToIgnore("MORFOLOGIK_RULE_EN_US,UPPERCASE_SENTENCE_START");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("spelling/no-spelling-mistake-rule-exclusions.feature"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_language_parameter_is_not_valid() {
    try {
      SpellingCheck check = new SpellingCheck();
      check.setLanguage("en_NZ");

      GherkinCheckVerifier.issues(check, CheckTestUtils.getTestFile("spelling/spelling.feature")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:spelling (Spelling mistakes should be fixed): "
        + "language parameter \"en_NZ\" is not valid. Allowed values are 'en_US' and 'en_GB'.");
    }
  }

}
