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
package org.sonar.gherkin.checks;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.gherkin.parser.GherkinDialectProvider;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.Set;

@Rule(
  key = "same-feature-language",
  name = "Features should be written in the same language",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("30min")
public class SameFeatureLanguageCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_LANGUAGE = GherkinDialectProvider.DEFAULT_LANGUAGE;

  private static final Set<String> SUPPORTED_LANGUAGES = ImmutableSet.of("af", "am", "ar", "ast", "az", "bg", "bm",
    "bs", "ca", "cs", "cy-GB", "da", "de", "el", "em", "en", "en-Scouse", "en-au", "en-lol", "en-old", "en-pirate",
    "eo", "es", "et", "fa", "fi", "fr", "ga", "gj", "gl", "he", "hi", "hr", "ht", "hu", "id", "is", "it", "ja", "jv",
    "ka", "kn", "ko", "lt", "lu", "lv", "mn", "nl", "no", "pa", "pl", "pt", "ro", "ru", "sk", "sl", "sr-Cyrl",
    "sr-Latn", "sv", "ta", "th", "tl", "tlh", "tr", "tt", "uk", "ur", "uz", "vi", "zh-CN", "zh-TW");

  private static final String SUPPORTED_LANGUAGES_AS_STRING = "af, am, ar, ast, az, bg, bm, bs, ca, cs, "
    + "cy-GB, da, de, el, em, en, en-Scouse, en-au, en-lol, en-old, en-pirate, eo, es, et, fa, fi, fr, ga, gj, gl, "
    + "he, hi, hr, ht, hu, id, is, it, ja, jv, ka, kn, ko, lt, lu, lv, mn, nl, no, pa, pl, pt, ro, ru, sk, sl, "
    + "sr-Cyrl, sr-Latn, sv, ta, th, tl, tlh, tr, tt, uk, ur, uz, vi, zh-CN, zh-TW";

  @RuleProperty(
    key = "language",
    description = "Language of the Gherkin documents. Allowed values are: " + SUPPORTED_LANGUAGES_AS_STRING,
    defaultValue = DEFAULT_LANGUAGE)
  private String language = DEFAULT_LANGUAGE;

  @Override
  public void visitGherkinDocument(GherkinDocumentTree tree) {
    if (!tree.language().equals(language)) {
      if (tree.languageDeclaration() != null) {
        addPreciseIssue(tree.languageDeclaration(), "Update the language definition to '" + language + "' and translate the content of the file.");
      } else {
        addFileIssue("Add a '" + language + "' language definition and translate the content of the file.");
      }
    }
  }

  @Override
  public void validateParameters() {
    if (!SUPPORTED_LANGUAGES.contains(language)) {
      throw new IllegalStateException(languageParamErrorMessage());
    }
  }

  private String languageParamErrorMessage() {
    return CheckUtils.paramErrorMessage(
      this.getClass(),
      "language parameter \"" + language + "\" is not valid. Allowed values are: " + SUPPORTED_LANGUAGES_AS_STRING);
  }

  @VisibleForTesting
  void setLanguage(String language) {
    this.language = language;
  }

}
