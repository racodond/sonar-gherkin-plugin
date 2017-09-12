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

import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.gherkin.api.tree.SyntaxTrivia;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitorCheck;

public class
CommentContainsPatternChecker extends DoubleDispatchVisitorCheck {

  private final String pattern;
  private final String message;

  public CommentContainsPatternChecker(String pattern, String message) {
    this.pattern = pattern;
    this.message = message;
  }

  @Override
  public void visitComment(SyntaxTrivia trivia) {
    String comment = trivia.text();
    if (StringUtils.containsIgnoreCase(comment, pattern) && !isLetterAround(comment, pattern)) {
      addPreciseIssue(trivia, message);
    }
    super.visitComment(trivia);
  }

  private static boolean isLetterAround(String line, String pattern) {
    int start = StringUtils.indexOfIgnoreCase(line, pattern);
    int end = start + pattern.length();

    boolean pre = start > 0 && Character.isLetter(line.charAt(start - 1));
    boolean post = end < line.length() - 1 && Character.isLetter(line.charAt(end));

    return pre || post;
  }

}
