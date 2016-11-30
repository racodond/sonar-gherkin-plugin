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
package org.sonar.plugins.gherkin;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.gherkin.parser.GherkinParserBuilder;
import org.sonar.gherkin.visitors.CharsetAwareVisitor;
import org.sonar.gherkin.visitors.GherkinVisitorContext;
import org.sonar.gherkin.visitors.SyntaxHighlighterVisitor;
import org.sonar.gherkin.visitors.metrics.MetricsVisitor;
import org.sonar.plugins.gherkin.api.CustomGherkinRulesDefinition;
import org.sonar.plugins.gherkin.api.GherkinCheck;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.TreeVisitor;
import org.sonar.plugins.gherkin.api.visitors.issue.Issue;
import org.sonar.gherkin.checks.ParsingErrorCheck;
import org.sonar.plugins.gherkin.issuesaver.CrossFileChecksIssueSaver;
import org.sonar.plugins.gherkin.issuesaver.IssueSaver;
import org.sonar.squidbridge.ProgressReport;
import org.sonar.squidbridge.api.AnalysisException;

import javax.annotation.Nullable;
import java.io.File;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GherkinSquidSensor implements Sensor {

  private static final Logger LOG = Loggers.get(GherkinSquidSensor.class);

  private final FileSystem fileSystem;
  private final GherkinChecks checks;
  private final ActionParser<Tree> parser;
  private final FilePredicate mainFilePredicate;
  private IssueSaver issueSaver;
  private RuleKey parsingErrorRuleKey = null;

  public GherkinSquidSensor(FileSystem fileSystem, CheckFactory checkFactory) {
    this(fileSystem, checkFactory, null);
  }

  public GherkinSquidSensor(FileSystem fileSystem, CheckFactory checkFactory, @Nullable CustomGherkinRulesDefinition[] customRulesDefinition) {
    this.fileSystem = fileSystem;

    this.mainFilePredicate = fileSystem.predicates().and(
      fileSystem.predicates().hasType(Type.MAIN),
      fileSystem.predicates().hasLanguage(GherkinLanguage.KEY));

    this.parser = GherkinParserBuilder.createParser(fileSystem.encoding());

    this.checks = GherkinChecks.createGherkinChecks(checkFactory)
      .addChecks(GherkinRulesDefinition.REPOSITORY_KEY, GherkinRulesDefinition.getChecks())
      .addCustomChecks(customRulesDefinition);
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .onlyOnLanguage(GherkinLanguage.KEY)
      .name("Gherkin Squid Sensor")
      .onlyOnFileType(Type.MAIN);
  }

  @Override
  public void execute(SensorContext sensorContext) {
    List<TreeVisitor> treeVisitors = Lists.newArrayList();
    treeVisitors.addAll(checks.visitorChecks());
    treeVisitors.add(new SyntaxHighlighterVisitor(sensorContext));
    treeVisitors.add(new MetricsVisitor(sensorContext));

    setParsingErrorCheckIfActivated(treeVisitors);

    ProgressReport progressReport = new ProgressReport("Report about progress of Gherkin analyzer", TimeUnit.SECONDS.toMillis(10));
    progressReport.start(Lists.newArrayList(fileSystem.files(mainFilePredicate)));

    issueSaver = new IssueSaver(sensorContext, checks);
    List<Issue> issues = new ArrayList<>();

    boolean success = false;
    try {
      for (InputFile inputFile : fileSystem.inputFiles(mainFilePredicate)) {
        issues.addAll(analyzeFile(sensorContext, inputFile, treeVisitors));
        progressReport.nextFile();
      }
      saveSingleFileIssues(issues);
      saveCrossFileIssues();
      success = true;
    } finally {
      stopProgressReport(progressReport, success);
    }
  }

  private List<Issue> analyzeFile(SensorContext sensorContext, InputFile inputFile, List<TreeVisitor> visitors) {
    try {
      GherkinDocumentTree gherkinDocument = (GherkinDocumentTree) parser.parse(new File(inputFile.absolutePath()));
      return scanFile(inputFile, gherkinDocument, visitors);

    } catch (RecognitionException e) {
      checkInterrupted(e);
      LOG.error("Unable to parse file: " + inputFile.absolutePath());
      LOG.error(e.getMessage());
      processRecognitionException(e, sensorContext, inputFile);

    } catch (Exception e) {
      checkInterrupted(e);
      throw new AnalysisException("Unable to analyse file: " + inputFile.absolutePath(), e);
    }
    return new ArrayList<>();
  }

  private List<Issue> scanFile(InputFile inputFile, GherkinDocumentTree gherkinDocument, List<TreeVisitor> visitors) {
    GherkinVisitorContext context = new GherkinVisitorContext(gherkinDocument, inputFile.file());
    List<Issue> issues = new ArrayList<>();
    for (TreeVisitor visitor : visitors) {
      if (visitor instanceof CharsetAwareVisitor) {
        ((CharsetAwareVisitor) visitor).setCharset(fileSystem.encoding());
      }
      if (visitor instanceof GherkinCheck) {
        issues.addAll(((GherkinCheck) visitor).scanFile(context));
      } else {
        visitor.scanTree(context);
      }
    }
    return issues;
  }

  private void saveSingleFileIssues(List<Issue> issues) {
    for (Issue issue : issues) {
      issueSaver.saveIssue(issue);
    }
  }

  private void saveCrossFileIssues() {
    CrossFileChecksIssueSaver.saveIssues(issueSaver);
  }

  private void processRecognitionException(RecognitionException e, SensorContext sensorContext, InputFile inputFile) {
    if (parsingErrorRuleKey != null) {
      NewIssue newIssue = sensorContext.newIssue();

      NewIssueLocation primaryLocation = newIssue.newLocation()
        .message(e.getMessage())
        .on(inputFile)
        .at(inputFile.selectLine(e.getLine()));

      newIssue
        .forRule(parsingErrorRuleKey)
        .at(primaryLocation)
        .save();
    }
  }

  private void setParsingErrorCheckIfActivated(List<TreeVisitor> treeVisitors) {
    for (TreeVisitor check : treeVisitors) {
      if (check instanceof ParsingErrorCheck) {
        parsingErrorRuleKey = checks.ruleKeyFor((GherkinCheck) check);
        break;
      }
    }
  }

  private static void stopProgressReport(ProgressReport progressReport, boolean success) {
    if (success) {
      progressReport.stop();
    } else {
      progressReport.cancel();
    }
  }

  private static void checkInterrupted(Exception e) {
    Throwable cause = Throwables.getRootCause(e);
    if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
      throw new AnalysisException("Analysis cancelled", e);
    }
  }

}
