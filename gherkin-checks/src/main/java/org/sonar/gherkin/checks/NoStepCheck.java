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
import org.sonar.plugins.gherkin.api.tree.BackgroundTree;
import org.sonar.plugins.gherkin.api.tree.ScenarioOutlineTree;
import org.sonar.plugins.gherkin.api.tree.ScenarioTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "no-step",
  name = "Scenarios that do not define any step should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class NoStepCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitBackground(BackgroundTree tree) {
    if (tree.steps().isEmpty()) {
      addPreciseIssue(tree.prefix(), "Remove this Background that does not define any step.");
    }
    super.visitBackground(tree);
  }

  @Override
  public void visitScenario(ScenarioTree tree) {
    if (tree.steps().isEmpty()) {
      addPreciseIssue(tree.prefix(), "Remove this Scenario that does not define any step.");
    }
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    if (tree.steps().isEmpty()) {
      addPreciseIssue(tree.prefix(), "Remove this Scenario Outline that does not define any step.");
    }
    super.visitScenarioOutline(tree);
  }

}
