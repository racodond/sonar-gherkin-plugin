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
  key = "use-and-but",
  name = "And and But prefixes should be used instead of redundant Given/When/Then prefixes",
  priority = Priority.MINOR,
  tags = {Tags.READABILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class UseAndButCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScenario(ScenarioTree tree) {
    checkRedundantStepPrefix(tree.steps());
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    checkRedundantStepPrefix(tree.steps());
    super.visitScenarioOutline(tree);
  }

  private void checkRedundantStepPrefix(List<StepTree> steps) {

    if (steps.size() < 2) {
      return;
    }

    StepTree.SemanticStepType previousStepType = steps.get(0).semanticType();

    for (int i = 1; i < steps.size(); i++) {
      if (previousStepType != StepTree.SemanticStepType.UNKNOWN
        && steps.get(i).semanticType() != StepTree.SemanticStepType.UNKNOWN
        && previousStepType == steps.get(i).semanticType()
        && steps.get(i).syntacticType() != StepTree.SyntacticStepType.AND
        && steps.get(i).syntacticType() != StepTree.SyntacticStepType.BUT) {

        addPreciseIssue(
          steps.get(i).prefix(),
          "Replace this redundant " + steps.get(i).semanticType().getValue() + " prefix with And or But prefix.");
      }

      previousStepType = steps.get(i).semanticType();
    }
  }

}
