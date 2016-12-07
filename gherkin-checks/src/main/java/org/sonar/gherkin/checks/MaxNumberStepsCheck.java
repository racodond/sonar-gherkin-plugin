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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.FeatureTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "max-number-steps",
  name = "Scenarios should not contain too many steps",
  priority = Priority.MAJOR,
  tags = {Tags.READABILITY})
@SqaleConstantRemediation("30min")
@ActivatedByDefault
public class MaxNumberStepsCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT = 12;

  @RuleProperty(
    key = "threshold",
    defaultValue = DEFAULT + "",
    description = "Maximum number of steps per scenario.")
  private int threshold = DEFAULT;

  @Override
  public void visitFeature(FeatureTree tree) {
    int numberOfBackgroundSteps = tree.background() != null ? tree.background().steps().size() : 0;

    tree.allScenarios()
      .stream()
      .filter(s -> s.steps().size() + numberOfBackgroundSteps > threshold)
      .forEach(s -> addPreciseIssue(s.prefix(), "Reduce the number of steps (" + (s.steps().size() + numberOfBackgroundSteps) + ", greater than " + threshold + " allowed)."));

    super.visitFeature(tree);
  }

  @VisibleForTesting
  void setThreshold(int threshold) {
    this.threshold = threshold;
  }


}
