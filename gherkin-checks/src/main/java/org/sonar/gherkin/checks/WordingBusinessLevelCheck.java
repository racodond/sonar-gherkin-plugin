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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.NameTree;
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Rule(
  key = "wording-business-level",
  name = "Wording should remain at business level",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class WordingBusinessLevelCheck extends DoubleDispatchVisitorCheck {

  protected static final String[] FORBIDDEN_WORDS = {"checkbox", "click", "drop-down list", "dropdown list", "field", "fill in the form", "radio button"};

  @Override
  public void visitStep(StepTree tree) {
    checkForForbiddenWords(tree.sentence().value());
    super.visitStep(tree);
  }

  @Override
  public void visitName(NameTree tree) {
    checkForForbiddenWords(tree.value());
    super.visitName(tree);
  }

  private void checkForForbiddenWords(SyntaxToken token) {
    List<String> forbiddenWordsFound = forbiddenWordsFound(token.text());
    if (!forbiddenWordsFound.isEmpty()) {
      addPreciseIssue(
        token,
        "Rephrase this sentence to remove the following forbidden words: "
          + forbiddenWordsFound.stream().sorted().collect(Collectors.joining(", ")));
    }
  }

  private List<String> forbiddenWordsFound(String text) {
    List<String> forbiddenWordsFound = new ArrayList<>();
    for (String word : FORBIDDEN_WORDS) {
      if (text.contains(word)) {
        forbiddenWordsFound.add(word);
      }
    }
    return forbiddenWordsFound.stream().sorted().collect(Collectors.toList());
  }

}
