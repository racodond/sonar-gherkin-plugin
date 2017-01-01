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
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.tree.TagTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;

@Rule(
  key = "allowed-tags",
  name = "Only tags from the whitelist should be used",
  priority = Priority.MINOR,
  tags = {Tags.TAG, Tags.CONVENTION})
@SqaleConstantRemediation("5min")
public class AllowedTagsCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT = "smoke,nrt";
  private List<String> listOfAllowedTags;

  @RuleProperty(
    key = "allowedTags",
    defaultValue = DEFAULT,
    description = "Comma-separated list of allowed tags.")
  private String allowedTags = DEFAULT;

  @Override
  public void visitGherkinDocument(GherkinDocumentTree tree) {
    listOfAllowedTags = Lists.newArrayList(Splitter.on(",").split(allowedTags));
    super.visitGherkinDocument(tree);
  }

  @Override
  public void visitTag(TagTree tree) {
    if (!listOfAllowedTags.contains(tree.text())) {
      addPreciseIssue(tree, "Remove this tag that is not in the whitelist.");
    }
    super.visitTag(tree);
  }

  @VisibleForTesting
  void setAllowedTags(String allowedTags) {
    this.allowedTags = allowedTags;
  }

}
