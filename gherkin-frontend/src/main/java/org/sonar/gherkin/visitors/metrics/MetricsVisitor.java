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

import com.google.common.collect.ImmutableList;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.SubscriptionVisitor;

import java.io.Serializable;
import java.util.List;

public class MetricsVisitor extends SubscriptionVisitor {

  private final SensorContext sensorContext;
  private final FileSystem fileSystem;
  private InputFile inputFile;

  public MetricsVisitor(SensorContext sensorContext) {
    this.sensorContext = sensorContext;
    this.fileSystem = sensorContext.fileSystem();
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.GHERKIN_DOCUMENT);
  }

  @Override
  public void visitFile(Tree tree) {
    this.inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
  }

  @Override
  public void leaveFile(Tree tree) {
    LinesOfCodeVisitor linesOfCodeVisitor = new LinesOfCodeVisitor(tree);
    saveMetricOnFile(CoreMetrics.NCLOC, linesOfCodeVisitor.getNumberOfLinesOfCode());

    CommentLinesVisitor commentLinesVisitor = new CommentLinesVisitor(tree);
    saveMetricOnFile(CoreMetrics.COMMENT_LINES, commentLinesVisitor.getNumberOfCommentLines());

    StatementsVisitor statementsVisitor = new StatementsVisitor(tree);
    saveMetricOnFile(CoreMetrics.STATEMENTS, statementsVisitor.getNumberOfStatements());

    FunctionsVisitor functionsVisitor = new FunctionsVisitor(tree);
    saveMetricOnFile(CoreMetrics.FUNCTIONS, functionsVisitor.getNumberOfFunctions());

    ClassesVisitor classesVisitor = new ClassesVisitor(tree);
    saveMetricOnFile(CoreMetrics.CLASSES, classesVisitor.getNumberOfClasses());
  }

  private <T extends Serializable> void saveMetricOnFile(Metric metric, T value) {
    sensorContext.<T>newMeasure()
      .withValue(value)
      .forMetric(metric)
      .on(inputFile)
      .save();
  }

}
