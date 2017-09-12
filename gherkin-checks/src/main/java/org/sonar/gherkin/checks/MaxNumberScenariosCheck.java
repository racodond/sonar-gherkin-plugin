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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.FeatureTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "max-number-scenarios",
  name = "Features should not contain too many scenarios",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("30min")
@ActivatedByDefault
public class MaxNumberScenariosCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT = 12;

  @RuleProperty(
    key = "threshold",
    defaultValue = DEFAULT + "",
    description = "Maximum number of scenarios per feature.")
  private int threshold = DEFAULT;

  @Override
  public void visitFeature(FeatureTree tree) {
    if (tree.allScenarios().size() > threshold) {
      addPreciseIssue(
        tree.declaration().prefix(),
        "The number of scenarios (" + tree.allScenarios().size() + ") is greater that the maximum allowed ("
          + threshold + "). Split the scenarios into different features.");
    }

    super.visitFeature(tree);
  }

  @VisibleForTesting
  void setThreshold(int threshold) {
    this.threshold = threshold;
  }


}
