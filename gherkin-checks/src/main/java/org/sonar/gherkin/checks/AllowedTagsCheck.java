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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.gherkin.api.tree.TagTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "allowed-tags",
  name = "Only tags matching a regular expression should be used",
  priority = Priority.MINOR,
  tags = {Tags.TAG, Tags.CONVENTION})
@SqaleConstantRemediation("5min")
public class AllowedTagsCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT = "smoke|nrt";

  @RuleProperty(
    key = "allowedTags",
    defaultValue = DEFAULT,
    description = "The regular expression that tags should match. See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.")
  private String allowedTags = DEFAULT;

  @Override
  public void visitTag(TagTree tree) {
    if (!tree.text().matches(allowedTags)) {
      addPreciseIssue(tree, "Remove this tag that does not match the regular expression: \"" + allowedTags + "\"");
    }
    super.visitTag(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(allowedTags);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramErrorMessage(), exception);
    }
  }

  private String paramErrorMessage() {
    return CheckUtils.paramErrorMessage(
      this.getClass(),
      "allowedTags parameter \"" + allowedTags + "\" is not a valid regular expression.");
  }

  @VisibleForTesting
  void setAllowedTags(String allowedTags) {
    this.allowedTags = allowedTags;
  }

}
