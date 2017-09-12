/*
 * SonarQube Cucumber Gherkin Analyzer
 * Copyright (C) 2016-2017 David RACODON
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

public class SameFeatureLanguageCheckTest {

  @Test
  public void should_be_written_in_the_default_language_and_not_raise_any_issue() {
    GherkinCheckVerifier.issues(
      new SameFeatureLanguageCheck(),
      CheckTestUtils.getTestFile("same-feature-language/default.feature"))
      .noMore();
  }

  @Test
  public void should_be_written_in_the_custom_language_and_not_raise_any_issue() {
    SameFeatureLanguageCheck check = new SameFeatureLanguageCheck();
    check.setLanguage("fr");

    GherkinCheckVerifier.issues(
      check,
      CheckTestUtils.getTestFile("same-feature-language/fr.feature"),
      "fr")
      .noMore();
  }

  @Test
  public void should_not_be_written_in_the_default_language_and_raise_an_issue() {
    GherkinCheckVerifier.issues(
      new SameFeatureLanguageCheck(),
      CheckTestUtils.getTestFile("same-feature-language/not-default.feature"),
      "fr")
      .next().atLine(1).withMessage("Update the language definition to 'en' and translate the content of the file.")
      .noMore();
  }

  @Test
  public void should_not_be_written_in_the_custom_language_and_raise_an_issue() {
    SameFeatureLanguageCheck check = new SameFeatureLanguageCheck();
    check.setLanguage("fr");

    GherkinCheckVerifier.issues(
      check,
      CheckTestUtils.getTestFile("same-feature-language/not-fr.feature"))
      .next().withMessage("Add a 'fr' language definition and translate the content of the file.")
      .noMore();
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_language_parameter_is_not_valid() {
    try {
      SameFeatureLanguageCheck check = new SameFeatureLanguageCheck();
      check.setLanguage("abc");

      GherkinCheckVerifier.issues(check, CheckTestUtils.getTestFile("same-feature-language/default.feature")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:same-feature-language (Features should be written in the same language): "
        + "language parameter \"abc\" is not valid. Allowed values are: af, am, ar, ast, az, bg, bm, bs, ca, cs, "
        + "cy-GB, da, de, el, em, en, en-Scouse, en-au, en-lol, en-old, en-pirate, eo, es, et, fa, fi, fr, ga, gj, gl, "
        + "he, hi, hr, ht, hu, id, is, it, ja, jv, ka, kn, ko, lt, lu, lv, mn, nl, no, pa, pl, pt, ro, ru, sk, sl, "
        + "sr-Cyrl, sr-Latn, sv, ta, th, tl, tlh, tr, tt, uk, ur, uz, vi, zh-CN, zh-TW");
    }
  }

}
