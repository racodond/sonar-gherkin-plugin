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
import org.sonar.plugins.gherkin.api.tree.BackgroundTree;
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "only-given-steps-in-background",
  name = "Non-Given steps should be moved out of Background",
  priority = Priority.CRITICAL,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class OnlyGivenStepsInBackgroundCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitBackground(BackgroundTree tree) {
    tree.steps()
      .stream()
      .filter(s -> s.type() != StepTree.StepType.GIVEN)
      .forEach(s -> addPreciseIssue(s, "Move this non-Given step out of Background."));

    super.visitBackground(tree);
  }

}
