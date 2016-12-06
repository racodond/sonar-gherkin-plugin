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
import org.sonar.plugins.gherkin.api.tree.StepTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "step-sentence-length",
  name = "Step sentences should not be too long",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class StepSentenceLengthCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_LENGTH = 100;

  @RuleProperty(
    key = "threshold",
    defaultValue = DEFAULT_LENGTH + "",
    description = "Maximum step sentence length.")
  private int threshold = DEFAULT_LENGTH;

  @Override
  public void visitStep(StepTree tree) {
    if (tree.sentence().text().length() > threshold) {
      addPreciseIssue(
        tree.sentence(),
        "Rephrase this sentence to make it shorter. Actual size: "
          + tree.sentence().text().length() + " characters. "
          + "Maximum expected size: "
          + threshold + " characters.");
    }
    super.visitStep(tree);
  }

  @VisibleForTesting
  void setThreshold(int threshold) {
    this.threshold = threshold;
  }


}
