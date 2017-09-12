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
import org.sonar.plugins.gherkin.api.tree.ExamplesTree;
import org.sonar.plugins.gherkin.api.tree.TableTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "incomplete-examples-table",
  name = "Examples data tables should contain data at least two data rows",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class IncompleteExamplesTableCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitExamples(ExamplesTree tree) {
    TableTree table = tree.table();

    if (table == null) {
      addPreciseIssue(tree.prefix(), "Add a data table to this Examples section.");
    } else if (table.rows().size() == 1) {
      addPreciseIssue(table, "Add data rows to this data table.");
    } else if (table.rows().size() < 3) {
      addPreciseIssue(table, "Add data rows to this data table or convert this Scenario Outline to a standard Scenario.");
    }

    super.visitExamples(tree);
  }

}
