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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.ScenarioOutlineTree;
import org.sonar.plugins.gherkin.api.tree.ScenarioTree;
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;

@Rule(
  key = "steps-right-order",
  name = "Given/When/Then steps should be defined in the right order",
  priority = Priority.CRITICAL,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class StepsRightOrderCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScenario(ScenarioTree tree) {
    checkStepOrder(tree.steps());
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    checkStepOrder(tree.steps());
    super.visitScenarioOutline(tree);
  }

  private void checkStepOrder(List<StepTree> steps) {

    if (steps.size() < 2 || steps.stream().filter(s -> s.semanticType() == StepTree.SemanticStepType.UNKNOWN).count() != 0) {
      return;
    }

    StepTree.SemanticStepType previousStepType = steps.get(0).semanticType();

    search:
    for (int i = 1; i < steps.size(); i++) {

      switch (steps.get(i).semanticType()) {
        case GIVEN:
          if (previousStepType != StepTree.SemanticStepType.GIVEN) {
            addPreciseIssue(steps.get(i).prefix(), "Unexpected Given step. Reorder the steps of this scenario.");
            break search;
          }
          break;

        case WHEN:
          if (previousStepType != StepTree.SemanticStepType.GIVEN && previousStepType != StepTree.SemanticStepType.WHEN) {
            addPreciseIssue(steps.get(i).prefix(), "Unexpected When step. Reorder the steps of this scenario.");
            break search;
          }
          break;

        case THEN:
          if (previousStepType != StepTree.SemanticStepType.WHEN && previousStepType != StepTree.SemanticStepType.THEN) {
            addPreciseIssue(steps.get(i).prefix(), "Unexpected Then step. Reorder the steps of this scenario.");
            break search;
          }
          break;

        default:
          break;
      }

      previousStepType = steps.get(i).semanticType();
    }
  }

}
