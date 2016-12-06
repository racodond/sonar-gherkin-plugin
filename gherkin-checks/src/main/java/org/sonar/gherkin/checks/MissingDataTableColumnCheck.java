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
import org.sonar.plugins.gherkin.api.tree.ScenarioOutlineTree;
import org.sonar.plugins.gherkin.api.tree.TableTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.Set;
import java.util.stream.Collectors;

@Rule(
  key = "missing-data-table-column",
  name = "Missing data table columns should be added",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class MissingDataTableColumnCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    TableTree table = tree.examples().table();

    Set<String> missingColumns = tree.steps()
      .stream()
      .flatMap(s -> s.variables().stream())
      .collect(Collectors.toSet());

    if (table != null) {
      missingColumns.removeAll(table.headers().stream().collect(Collectors.toSet()));
    }

    if (!missingColumns.isEmpty()) {
      if (table != null) {
        addPreciseIssue(
          tree.examples().prefix(),
          "Add the following missing data table column" + (missingColumns.size() > 1 ? "s" : "") + ": "
            + missingColumns.stream().sorted().collect(Collectors.joining(", ")));
      } else {
        addPreciseIssue(
          tree.examples().prefix(),
          "Add a data table with the following missing column" + (missingColumns.size() > 1 ? "s" : "") + ": "
            + missingColumns.stream().sorted().collect(Collectors.joining(", ")));
      }
    }

    super.visitScenarioOutline(tree);
  }

}
