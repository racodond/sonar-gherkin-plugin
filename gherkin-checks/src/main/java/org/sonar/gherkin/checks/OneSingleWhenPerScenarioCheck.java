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

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.BasicScenarioTree;
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.SubscriptionVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;
import java.util.stream.Collectors;

@Rule(
  key = "one-single-when-per-scenario",
  name = "There should be one single When step per scenario",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class OneSingleWhenPerScenarioCheck extends SubscriptionVisitorCheck {

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.SCENARIO, Tree.Kind.SCENARIO_OUTLINE);
  }

  @Override
  public void visitNode(Tree tree) {
    List<StepTree> whenSteps = ((BasicScenarioTree) tree).steps()
      .stream()
      .filter(s -> s.semanticType() == StepTree.SemanticStepType.WHEN)
      .collect(Collectors.toList());

    if (whenSteps.size() > 1) {
      PreciseIssue issue = addPreciseIssue(whenSteps.get(0), "Merge all the When steps or split the scenario up in multiple scenarios.");
      for (int i = 1; i < whenSteps.size(); i++) {
        issue.secondary(whenSteps.get(i), "When step");
      }
    }
  }

}
