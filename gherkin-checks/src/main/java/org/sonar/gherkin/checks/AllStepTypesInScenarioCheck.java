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
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rule(
  key = "all-step-types-in-scenario",
  name = "Each scenario should define at least one of each Given/When/Then step",
  priority = Priority.MAJOR,
  tags = {Tags.READABILITY})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class AllStepTypesInScenarioCheck extends DoubleDispatchVisitorCheck {

  private List<StepTree> backgroundSteps = new ArrayList<>();

  @Override
  public void visitFeature(FeatureTree tree) {
    if (tree.background() != null) {
      backgroundSteps = tree.background().steps();
    } else {
      backgroundSteps.clear();
    }
    super.visitFeature(tree);
  }

  @Override
  public void visitScenario(ScenarioTree tree) {
    checkAllStepTypes(tree);
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    checkAllStepTypes(tree);
    super.visitScenarioOutline(tree);
  }

  private void checkAllStepTypes(BasicScenarioTree tree) {
    int givens = 0;
    int whens = 0;
    int thens = 0;

    for (StepTree step : Stream.concat(backgroundSteps.stream(), tree.steps().stream()).collect(Collectors.toList())) {
      switch (step.type()) {
        case GIVEN:
          givens++;
          break;
        case WHEN:
          whens++;
          break;
        case THEN:
          thens++;
          break;
        default:
          break;
      }
    }

    if (givens == 0) {
      addPreciseIssue(tree.prefix(), "Add at least one Given step.");
    }
    if (whens == 0) {
      addPreciseIssue(tree.prefix(), "Add at least one When step.");
    }
    if (thens == 0) {
      addPreciseIssue(tree.prefix(), "Add at least one Then step.");
    }
  }

}
