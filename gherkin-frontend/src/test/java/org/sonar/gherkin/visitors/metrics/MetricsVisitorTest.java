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
package org.sonar.gherkin.visitors.metrics;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.gherkin.parser.GherkinParserBuilder;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.visitors.TreeVisitorContext;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetricsVisitorTest {

  @Test
  public void test() {
    File moduleBaseDir = new File("src/test/resources/metrics/");
    SensorContextTester context = SensorContextTester.create(moduleBaseDir);

    DefaultInputFile inputFile = new DefaultInputFile("moduleKey", "metrics.feature")
      .setModuleBaseDir(moduleBaseDir.toPath())
      .setLanguage("gherkin")
      .setType(InputFile.Type.MAIN);

    context.fileSystem().add(inputFile);

    MetricsVisitor metricsVisitor = new MetricsVisitor(context);

    TreeVisitorContext treeVisitorContext = mock(TreeVisitorContext.class);
    when(treeVisitorContext.getFile()).thenReturn(inputFile.file());
    when(treeVisitorContext.getTopTree()).thenReturn((GherkinDocumentTree) GherkinParserBuilder.createTestParser(Charsets.UTF_8).parse(inputFile.file()));

    metricsVisitor.scanTree(treeVisitorContext);

    String componentKey = "moduleKey:metrics.feature";
    assertThat(context.measure(componentKey, CoreMetrics.NCLOC).value()).isEqualTo(22);
    assertThat(context.measure(componentKey, CoreMetrics.STATEMENTS).value()).isEqualTo(10);
    assertThat(context.measure(componentKey, CoreMetrics.COMMENT_LINES).value()).isEqualTo(2);
    assertThat(context.measure(componentKey, CoreMetrics.FUNCTIONS).value()).isEqualTo(4);
    assertThat(context.measure(componentKey, CoreMetrics.CLASSES).value()).isEqualTo(1);
  }

}
