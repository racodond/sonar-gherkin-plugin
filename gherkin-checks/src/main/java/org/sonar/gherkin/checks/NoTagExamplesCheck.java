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
import org.sonar.plugins.gherkin.api.tree.ExamplesTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "no-tag-examples",
  name = "Tags should not be set on Examples",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION, Tags.TAG})
@SqaleConstantRemediation("2min")
public class NoTagExamplesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitExamples(ExamplesTree tree) {
    if (!tree.tags().isEmpty()) {
      PreciseIssue issue = addPreciseIssue(tree.prefix(), "Move these tags up to the Scenario Outline.");
      tree.tags().forEach(t -> issue.secondary(t, ""));
    }
  }

}
