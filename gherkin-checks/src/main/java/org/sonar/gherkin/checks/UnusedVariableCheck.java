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
import org.sonar.plugins.gherkin.api.tree.ScenarioOutlineTree;
import org.sonar.plugins.gherkin.api.tree.TableTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Rule(
  key = "unused-variable",
  name = "Unused variables should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.BUG, Tags.UNUSED})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class UnusedVariableCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {

    Set<String> allVariables = tree.steps()
      .stream()
      .flatMap(s -> s.variables().stream())
      .collect(Collectors.toSet());

    for (ExamplesTree examplesTree : tree.examples()) {
      checkUnusedDataTableColumn(examplesTree, allVariables);
    }

    super.visitScenarioOutline(tree);
  }

  private void checkUnusedDataTableColumn(ExamplesTree examples, Set<String> allVariables) {
    TableTree table = examples.table();

    Set<String> unusedVariables = new HashSet<>();
    if (table != null) {
      unusedVariables = new HashSet<>(table.headers());
      unusedVariables.removeAll(allVariables);
    }

    if (!unusedVariables.isEmpty()) {
      addPreciseIssue(
        examples.prefix(),
        "Remove the following unused variable" + (unusedVariables.size() > 1 ? "s" : "") + ": "
          + unusedVariables.stream().sorted().collect(Collectors.joining(", ")));
    }

  }

}
