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
package org.sonar.gherkin.visitors.metrics;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.gherkin.parser.GherkinParserBuilder;
import org.sonar.plugins.gherkin.api.tree.Tree;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class MetricsTest {

  @Test
  public void metrics_UTF8_file() {
    String path = "src/test/resources/metrics/metrics.feature";
    Tree tree = GherkinParserBuilder.createTestParser(Charsets.UTF_8).parse(new File(path));
    assertMetrics(tree);
  }

  @Test
  public void metrics_UTF8_file_with_BOM() {
    String path = "src/test/resources/metrics/metrics-bom.feature";
    Tree tree = GherkinParserBuilder.createTestParser(Charsets.UTF_8).parse(new File(path));
    assertMetrics(tree);
  }

  private void assertMetrics(Tree tree) {
    assertThat(new LinesOfCodeVisitor(tree).getNumberOfLinesOfCode()).isEqualTo(22);
    assertThat(new StatementsVisitor(tree).getNumberOfStatements()).isEqualTo(10);
    assertThat(new CommentLinesVisitor(tree).getNumberOfCommentLines()).isEqualTo(2);
    assertThat(new FunctionsVisitor(tree).getNumberOfFunctions()).isEqualTo(4);
    assertThat(new ClassesVisitor(tree).getNumberOfClasses()).isEqualTo(1);
  }

}
