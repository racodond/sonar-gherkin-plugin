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
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "tag-naming-convention",
  name = "Tags should comply with a naming convention",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION, Tags.TAG})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class TagNameCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT = "^[a-z][-a-z0-9]*$";

  @RuleProperty(
    key = "format",
    defaultValue = DEFAULT,
    description = "Regular expression that tags should match. See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.")
  private String format = DEFAULT;

  @Override
  public void visitTag(TagTree tree) {
    if (!tree.text().matches(format)) {
      addPreciseIssue(tree, "Rename this tag to match the regular expression: " + format);
    }
    super.visitTag(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(format);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(
        CheckUtils.paramErrorMessage(this.getClass(), "format parameter \"" + format + "\" is not a valid regular expression."),
        exception);
    }
  }

  @VisibleForTesting
  void setFormat(String format) {
    this.format = format;
  }

}
