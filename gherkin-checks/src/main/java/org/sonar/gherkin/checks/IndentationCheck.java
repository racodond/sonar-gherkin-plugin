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
import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.util.List;

@Rule(
  key = "indentation",
  name = "Source code should be properly indented",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION, Tags.READABILITY})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class IndentationCheck extends SubscriptionVisitorCheck {

  private static final int DEFAULT = 2;

  @RuleProperty(
    key = "indentation",
    defaultValue = DEFAULT + "",
    description = "Number of whitespaces")
  private int indentation = DEFAULT;

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.FEATURE_DECLARATION,
      Tree.Kind.BACKGROUND,
      Tree.Kind.SCENARIO,
      Tree.Kind.SCENARIO_OUTLINE,
      Tree.Kind.EXAMPLES,
      Tree.Kind.STEP,
      Tree.Kind.FEATURE_PREFIX,
      Tree.Kind.BACKGROUND_PREFIX,
      Tree.Kind.SCENARIO_PREFIX,
      Tree.Kind.SCENARIO_OUTLINE_PREFIX,
      Tree.Kind.EXAMPLES_PREFIX,
      Tree.Kind.STEP_PREFIX,
      Tree.Kind.TABLE,
      Tree.Kind.DOC_STRING
    );
  }

  @Override
  public void visitNode(Tree tree) {
    checkAllPrefixesIndentation(tree);
    checkAllDescriptionsIndentation(tree);
    checkAllTagsIndentation(tree);
    checkAllTablesIndentation(tree);
    checkDocStringsIndentation(tree);
  }

  @VisibleForTesting
  void setIndentation(int indentation) {
    this.indentation = indentation;
  }

  private void checkAllDescriptionsIndentation(Tree tree) {
    if (tree instanceof Descriptionable) {
      int expectedIndentation;

      switch (tree.getKind()) {
        case FEATURE_DECLARATION:
          expectedIndentation = indentation;
          break;

        case BACKGROUND:
        case SCENARIO:
        case SCENARIO_OUTLINE:
          expectedIndentation = indentation * 2;
          break;

        case EXAMPLES:
          expectedIndentation = indentation * 3;
          break;

        default:
          throw new IllegalStateException("Unsupported Descriptionable: " + tree.toString());
      }

      checkDescriptionLinesIndentation(((Descriptionable) tree).description(), expectedIndentation);
    }
  }

  private void checkDescriptionLinesIndentation(@Nullable DescriptionTree description, int expectedIndentation) {
    if (description != null) {
      description.descriptionLines().forEach(d -> checkIndentation(d, expectedIndentation));
    }
  }

  private void checkAllPrefixesIndentation(Tree tree) {
    if (tree instanceof PrefixTree) {
      int expectedIndentation;

      switch (tree.getKind()) {
        case FEATURE_PREFIX:
          expectedIndentation = 0;
          break;

        case BACKGROUND_PREFIX:
        case SCENARIO_PREFIX:
        case SCENARIO_OUTLINE_PREFIX:
          expectedIndentation = indentation;
          break;

        case EXAMPLES_PREFIX:
        case STEP_PREFIX:
          expectedIndentation = indentation * 2;
          break;

        default:
          throw new IllegalStateException("Unsupported PrefixTree: " + tree.toString());
      }

      checkIndentation(((PrefixTree) tree).keyword(), expectedIndentation);
    }
  }

  private void checkAllTagsIndentation(Tree tree) {
    if (tree instanceof Taggable) {
      int expectedIndentation;

      switch (tree.getKind()) {
        case FEATURE_DECLARATION:
          expectedIndentation = 0;
          break;

        case BACKGROUND:
        case SCENARIO:
        case SCENARIO_OUTLINE:
          expectedIndentation = indentation;
          break;

        case EXAMPLES:
          expectedIndentation = indentation * 2;
          break;

        default:
          throw new IllegalStateException("Unsupported Taggable: " + tree.toString());
      }

      checkTagsIndentation(((Taggable) tree).tags(), expectedIndentation);
    }

  }

  private void checkTagsIndentation(List<TagTree> tags, int expectedIndentation) {
    int previousTagLine = 0;
    for (TagTree tag : tags) {
      if (tag.prefix().line() != previousTagLine) {
        checkIndentation(tag.prefix(), expectedIndentation);
        previousTagLine = tag.prefix().line();
      }
    }
  }

  private void checkAllTablesIndentation(Tree tree) {
    if (tree.is(Tree.Kind.TABLE)) {
      ((TableTree) tree).rows().forEach(d -> checkIndentation(d, indentation * 3));
    }
  }

  private void checkDocStringsIndentation(Tree tree) {
    if (tree.is(Tree.Kind.DOC_STRING)) {
      checkIndentation(((DocStringTree) tree).prefix(), indentation * 3);
      checkIndentation(((DocStringTree) tree).suffix(), indentation * 3);
    }
  }

  private void checkIndentation(SyntaxToken token, int expectedIndentation) {
    if (token.column() != expectedIndentation) {
      addLineIssue(
        token.line(),
        "Indent this line at column " + expectedIndentation + " (currently indented at column " + token.column() + ").");
    }
  }

}
