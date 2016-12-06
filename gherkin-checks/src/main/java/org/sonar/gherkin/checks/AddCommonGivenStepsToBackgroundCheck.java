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
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;

@Rule(
  key = "add-common-given-steps-to-background",
  name = "Common Given steps should be added to Background",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class AddCommonGivenStepsToBackgroundCheck extends DoubleDispatchVisitorCheck {

  private final List<List<StepTree>> allSteps = new ArrayList<>();

  @Override
  public void visitGherkinDocument(GherkinDocumentTree tree) {
    allSteps.clear();

    super.visitGherkinDocument(tree);

    if (tree.feature() != null) {
      checkForCommonGivenStepsToAddToBackground(tree.feature());
    }
  }

  @Override
  public void visitScenario(ScenarioTree tree) {
    allSteps.add(tree.steps());
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    allSteps.add(tree.steps());
    super.visitScenarioOutline(tree);
  }

  private void checkForCommonGivenStepsToAddToBackground(FeatureTree feature) {
    if (feature.allScenarios().size() > 1) {
      int lastCommonStepRank = findLastCommonGivenStepRank();
      if (lastCommonStepRank > 0) {
        createIssue(feature, lastCommonStepRank);
      }
    }
  }

  private int findLastCommonGivenStepRank() {
    int lastCommonStepRank = 0;

    search:
    for (int i = 0; i < allSteps.get(0).size(); i++) {
      if (allSteps.get(0).get(i).type() == StepTree.StepType.GIVEN) {
        for (int j = 1; j < allSteps.size(); j++) {
          if (allSteps.get(j).size() <= i
            || allSteps.get(j).get(i).type() != StepTree.StepType.GIVEN
            || !allSteps.get(j).get(i).sentence().text().equals(allSteps.get(0).get(i).sentence().text())) {
            break search;
          }
          if (j == allSteps.size() - 1) {
            lastCommonStepRank = i;
          }
        }
      }
    }

    return lastCommonStepRank;
  }

  private void createIssue(FeatureTree feature, int lastCommonStepRank) {
    PreciseIssue issue;

    if (feature.background() != null) {
      issue = addPreciseIssue(feature.background().prefix(), "Add all common Given steps to Background.");
    } else {
      issue = addPreciseIssue(feature.declaration().prefix(), "Add a Background to group common Given steps.");
    }

    for (List<StepTree> steps : allSteps) {
      for (int i = 0; i <= lastCommonStepRank; i++) {
        issue.secondary(steps.get(i), "Move to Background");
      }
    }
  }

}
