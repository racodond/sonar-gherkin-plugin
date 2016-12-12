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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.BritishEnglish;
import org.languagetool.language.English;
import org.languagetool.rules.spelling.SpellingCheckRule;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.DescriptionTree;
import org.sonar.plugins.gherkin.api.tree.LiteralTree;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Rule(
  key = "spelling",
  name = "Spelling mistakes should be fixed",
  priority = Priority.INFO,
  tags = {Tags.READABILITY})
@SqaleConstantRemediation("2min")
public class SpellingCheck extends SubscriptionVisitorCheck {

  private static final String DEFAULT_WORDS_TO_IGNORE = "";
  private static final String DEFAULT_LANGUAGE = "en_US";
  private static final Set<String> SUPPORTED_LANGUAGES = ImmutableSet.of("en_US", "en_GB");
  private JLanguageTool languageTool;

  @RuleProperty(
    key = "wordsToIgnore",
    description = "Comma-separated list of words to ignore. Example: 'blabla,toto'",
    defaultValue = DEFAULT_WORDS_TO_IGNORE)
  private String wordsToIgnore = DEFAULT_WORDS_TO_IGNORE;

  @RuleProperty(
    key = "language",
    description = "The language to be applied by the spell checker. Supported values are: 'en_US', 'en_GB'.",
    defaultValue = DEFAULT_LANGUAGE)
  private String language = DEFAULT_LANGUAGE;

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.NAME, Tree.Kind.DESCRIPTION, Tree.Kind.STEP_SENTENCE);
  }

  @Override
  public void visitFile(Tree tree) {
    languageTool = createLanguageTool();
  }

  public void visitNode(Tree tree) {
    if (tree instanceof LiteralTree) {
      checkSpellingMistakes(((LiteralTree) tree).text(), ((LiteralTree) tree).value());
    } else if (tree instanceof DescriptionTree) {
      ((DescriptionTree) tree).descriptionLines().forEach(r -> checkSpellingMistakes(r.text(), r));
    } else {
      throw new IllegalStateException("Unsupported tree type");
    }
  }

  private void checkSpellingMistakes(String text, SyntaxToken token) {
    try {
      languageTool.check(text)
        .forEach(m -> addPreciseIssue(
          token,
          m.getFromPos(),
          m.getToPos(),
          m.getMessage() + ". Suggested correction(s): " + m.getSuggestedReplacements() + "."));

    } catch (IOException e) {
      throw new IllegalStateException("Spell checker failed", e);
    }
  }

  private JLanguageTool createLanguageTool() {
    English dictionary;

    switch (language) {
      case "en_US":
        dictionary = new AmericanEnglish();
        break;

      case "en_GB":
        dictionary = new BritishEnglish();
        break;

      default:
        throw new IllegalStateException("Unsupported spell check language: " + language);
    }

    JLanguageTool languageTool = new JLanguageTool(dictionary);
    languageTool.getAllActiveRules()
      .stream()
      .filter(r -> r instanceof SpellingCheckRule)
      .forEach(r -> ((SpellingCheckRule) r).addIgnoreTokens(Arrays.asList(wordsToIgnore.split(","))));

    return languageTool;
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
      "language parameter \"" + language + "\" is not valid. Allowed values are 'en_US' and 'en_GB'.");
  }

  @VisibleForTesting
  void setLanguage(String language) {
    this.language = language;
  }

  @VisibleForTesting
  void setWordsToIgnore(String wordsToIgnore) {
    this.wordsToIgnore = wordsToIgnore;
  }

}
