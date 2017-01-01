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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.FeatureDeclarationTree;
import org.sonar.plugins.gherkin.api.tree.NameTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Rule(
  key = "duplicated-feature-names",
  name = "Features should have a unique name",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL, Tags.DESIGN})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicatedFeatureNamesCheck extends DoubleDispatchVisitorCheck {

  private Map<String, List<FileNameTree>> names = new HashMap<>();

  @Override
  public void visitFeatureDeclaration(FeatureDeclarationTree tree) {
    NameTree nameTree = tree.name();
    if (nameTree != null) {
      if (names.containsKey(nameTree.text())) {
        names.get(nameTree.text()).add(new FileNameTree(getContext().getFile(), nameTree));
      } else {
        names.put(nameTree.text(), Lists.newArrayList(new FileNameTree(getContext().getFile(), nameTree)));
      }
    }
  }

  public Map<String, List<FileNameTree>> getNames() {
    return names;
  }

  @VisibleForTesting
  void setNames(Map<String, List<FileNameTree>> names) {
    this.names = names;
  }

}
