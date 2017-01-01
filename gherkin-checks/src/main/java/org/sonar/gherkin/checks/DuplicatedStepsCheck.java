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

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Rule(
  key = "duplicated-steps",
  name = "Duplicated steps should be removed",
  priority = Priority.CRITICAL,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class DuplicatedStepsCheck extends DoubleDispatchVisitorCheck {

  private Map<String, List<StepTree>> backgroundStepsBySentence = new HashMap<>();

  @Override
  public void visitFeature(FeatureTree tree) {
    backgroundStepsBySentence.clear();
    if (tree.background() != null) {
      backgroundStepsBySentence = getStepsBySentence(tree.background().steps());
    }
    super.visitFeature(tree);
  }

  @Override
  public void visitBackground(BackgroundTree tree) {
    checkForDuplicatedStepsInBackground();
  }

  @Override
  public void visitScenario(ScenarioTree tree) {
    checkForDuplicatedStepsInScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    checkForDuplicatedStepsInScenario(tree);
  }

  private void checkForDuplicatedStepsInBackground() {
    backgroundStepsBySentence
      .entrySet()
      .stream()
      .filter(sentence -> sentence.getValue().size() > 1)
      .forEach(sentence -> createIssue(sentence.getValue()));
  }

  private void checkForDuplicatedStepsInScenario(BasicScenarioTree tree) {
    getStepsBySentence(tree.steps())
      .entrySet()
      .stream()
      .filter(sentence -> sentence.getValue().size() > 1 || backgroundStepsBySentence.keySet().contains(sentence.getKey()))
      .forEach(sentence -> createIssue(
        sentence.getValue(),
        backgroundStepsBySentence.get(sentence.getKey())));
  }

  private Map<String, List<StepTree>> getStepsBySentence(List<StepTree> steps) {
    Map<String, List<StepTree>> stepsBySentence = new HashMap<>();

    for (StepTree step : steps) {
      if (stepsBySentence.get(step.sentence().text()) == null) {
        stepsBySentence.put(step.sentence().text(), Lists.newArrayList(step));
      } else {
        stepsBySentence.get(step.sentence().text()).add(step);
      }
    }

    return stepsBySentence;
  }

  private void createIssue(List<StepTree> duplicatedSteps) {
    createIssue(duplicatedSteps, null);
  }

  private void createIssue(List<StepTree> duplicatedSteps, @Nullable List<StepTree> duplicatedStepsInBackground) {
    Preconditions.checkElementIndex(0, duplicatedSteps.size());

    PreciseIssue issue = addPreciseIssue(duplicatedSteps.get(0), "Remove this duplicated step.");
    addSecondaryLocation(issue, duplicatedSteps.stream().skip(1).collect(Collectors.toList()));

    if (duplicatedStepsInBackground != null) {
      addSecondaryLocation(issue, duplicatedStepsInBackground);
    }
  }

  private void addSecondaryLocation(PreciseIssue issue, List<StepTree> secondaryLocations) {
    secondaryLocations.forEach(s -> issue.secondary(s, "Duplicate"));
  }

}
