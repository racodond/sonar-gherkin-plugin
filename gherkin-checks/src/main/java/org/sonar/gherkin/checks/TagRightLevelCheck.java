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
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Rule(
  key = "tag-right-level",
  name = "Tags should be defined at the right level",
  priority = Priority.MINOR,
  tags = {Tags.TAG, Tags.READABILITY})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class TagRightLevelCheck extends DoubleDispatchVisitorCheck {

  private PrefixTree featurePrefix;
  private final Set<String> featureTags = new HashSet<>();
  private final List<TagTree> scenarioTagTrees = new ArrayList<>();
  private final List<Set<String>> scenarioTags = new ArrayList<>();

  @Override
  public void visitGherkinDocument(GherkinDocumentTree tree) {
    featureTags.clear();
    scenarioTagTrees.clear();
    scenarioTags.clear();

    super.visitGherkinDocument(tree);

    raiseIssueOnTagsNotDefinedAtRightLevel();
  }

  @Override
  public void visitFeatureDeclaration(FeatureDeclarationTree tree) {
    featureTags.addAll(tree.tags().stream().map(TagTree::text).collect(Collectors.toSet()));
    featurePrefix = tree.prefix();
    super.visitFeatureDeclaration(tree);
  }

  @Override
  public void visitScenario(ScenarioTree tree) {
    scenarioTagTrees.addAll(new ArrayList<>(tree.tags()));
    scenarioTags.add(tree.tags().stream().map(TagTree::text).collect(Collectors.toSet()));
    super.visitScenario(tree);
  }

  @Override
  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    List<TagTree> allTags = new ArrayList<>(tree.tags());
    if (tree.examples().size() == 1) {
      allTags.addAll(new ArrayList<>(tree.examples().get(0).tags()));
    }

    scenarioTagTrees.addAll(new ArrayList<>(allTags));
    scenarioTags.add(allTags.stream().map(TagTree::text).collect(Collectors.toSet()));

    super.visitScenarioOutline(tree);
  }

  private void raiseIssueOnTagsNotDefinedAtRightLevel() {
    if (!scenarioTags.isEmpty()) {
      Set<String> tagsSetToAllScenarios = scenarioTags.get(0);
      for (int i = 1; i < scenarioTags.size(); i++) {
        tagsSetToAllScenarios.retainAll(scenarioTags.get(i));
      }
      tagsSetToAllScenarios.removeAll(featureTags);

      for (String tag : tagsSetToAllScenarios) {
        PreciseIssue issue = addPreciseIssue(featurePrefix, "Add tag \"" + tag + "\" to this feature and remove it from all scenarios.");
        scenarioTagTrees.stream().filter(t -> tag.equals(t.text())).forEach(t -> issue.secondary(t, "Remove this tag"));
      }
    }
  }

}
