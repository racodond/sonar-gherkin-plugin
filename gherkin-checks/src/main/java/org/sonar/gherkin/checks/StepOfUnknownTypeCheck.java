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
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "step-of-unknown-type",
  name = "Steps of unknown type should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class StepOfUnknownTypeCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStep(StepTree tree) {
    if (tree.semanticType() == StepTree.SemanticStepType.UNKNOWN) {
      addPreciseIssue(tree.prefix(), "Update the prefix of this unknown type step.");
    }
    super.visitStep(tree);
  }

}
