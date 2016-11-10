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

import com.google.common.collect.ImmutableSet;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.gherkin.api.tree.TagTree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.Set;

@Rule(
  key = "forbidden-tag",
  priority = Priority.MAJOR,
  name = "Forbidden tag should not be used",
  tags = {"convention"})
@SqaleConstantRemediation("5min")
public class ForbiddenTagCheck extends DoubleDispatchVisitorCheck {

  private static final Set<String> FORBIDDEN_TAGS = ImmutableSet.of("foo", "bar");

  @Override
  public void visitTag(TagTree tree) {
    if (FORBIDDEN_TAGS.contains(tree.text().toLowerCase())) {
      addPreciseIssue(tree, "Remove this usage of the forbidden \"" + tree.text() + "\" tag.");
    }
    // super method must be called in order to visit what is under the key node in the syntax tree
    super.visitTag(tree);
  }

}
