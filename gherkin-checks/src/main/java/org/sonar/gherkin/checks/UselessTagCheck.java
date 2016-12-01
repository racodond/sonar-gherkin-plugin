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
import org.sonar.plugins.gherkin.api.tree.FeatureDeclarationTree;
import org.sonar.plugins.gherkin.api.tree.TagTree;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Rule(
  key = "useless-tag",
  name = "Useless tags should be removed",
  priority = Priority.MINOR,
  tags = {Tags.TAG, Tags.READABILITY})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class UselessTagCheck extends DoubleDispatchVisitorCheck {

  private Set<String> featureTags;
  private List<TagTree> featureTagTrees;

  @Override
  public void visitFeatureDeclaration(FeatureDeclarationTree tree) {
    featureTags = tree.tags().stream().map(TagTree::text).collect(Collectors.toSet());
    featureTagTrees = tree.tags();
    super.visitFeatureDeclaration(tree);
  }

  @Override
  public void visitTag(TagTree tree) {
    if (!tree.parent().is(Tree.Kind.FEATURE_DECLARATION) && featureTags.contains(tree.text())) {
      PreciseIssue issue = addPreciseIssue(tree, "Remove this useless tag that is already set at feature level.");
      featureTagTrees
        .stream()
        .filter(t -> t.text().equals(tree.text()))
        .forEach(t -> issue.secondary(t, ""));
    }
    super.visitTag(tree);
  }

}
