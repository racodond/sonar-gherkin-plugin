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
package org.sonar.plugins.gherkin.api.visitors;

import org.sonar.gherkin.visitors.Issues;
import org.sonar.plugins.gherkin.api.GherkinCheck;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.issue.FileIssue;
import org.sonar.plugins.gherkin.api.visitors.issue.Issue;
import org.sonar.plugins.gherkin.api.visitors.issue.LineIssue;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;

import java.util.List;

public abstract class DoubleDispatchVisitorCheck extends DoubleDispatchVisitor implements GherkinCheck {

  private Issues issues = new Issues(this);

  @Override
  public List<Issue> scanFile(TreeVisitorContext context) {
    validateParameters();
    issues.reset();
    scanTree(context);
    return issues.getList();
  }

  @Override
  public PreciseIssue addPreciseIssue(Tree tree, String message) {
    return issues.addPreciseIssue(getContext().getFile(), tree, message);
  }

  @Override
  public PreciseIssue addPreciseIssue(SyntaxToken token, int startOffset, int endOffset, String message) {
    return issues.addPreciseIssue(getContext().getFile(), token, startOffset, endOffset, message);
  }

  @Override
  public FileIssue addFileIssue(String message) {
    return issues.addFileIssue(getContext().getFile(), message);
  }

  @Override
  public LineIssue addLineIssue(int line, String message) {
    return issues.addLineIssue(getContext().getFile(), line, message);
  }

  @Override
  public void validateParameters() {
    // Default behavior: do nothing
  }

}
