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
package org.sonar.plugins.gherkin.issuesaver.crossfile;

import org.sonar.gherkin.checks.DuplicatedFeatureNamesCheck;
import org.sonar.gherkin.checks.FileNameTree;
import org.sonar.plugins.gherkin.api.visitors.issue.IssueLocation;
import org.sonar.plugins.gherkin.api.visitors.issue.PreciseIssue;
import org.sonar.plugins.gherkin.issuesaver.IssueSaver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DuplicatedFeatureNamesIssueSaver extends CrossFileCheckIssueSaver {

  public DuplicatedFeatureNamesIssueSaver(IssueSaver issueSaver) {
    super(issueSaver);
  }

  @Override
  public void saveIssues() {
    Optional<DuplicatedFeatureNamesCheck> check = getIssueSaver().getCheck(DuplicatedFeatureNamesCheck.class);

    if (check.isPresent()) {
      check.get().getNames().entrySet()
        .stream()
        .filter(entry -> isFeatureNameDuplicated(entry.getValue()))
        .forEach(entry -> saveIssue(check.get(), entry));
    }
  }

  private void saveIssue(DuplicatedFeatureNamesCheck check, Map.Entry<String, List<FileNameTree>> entry) {
    getIssueSaver().saveIssue(
      new PreciseIssue(
        check,
        new IssueLocation(entry.getValue().get(0).getFile(),
          entry.getValue().get(0).getName(),
          buildIssueMessage(entry))));
  }

  private String buildIssueMessage(Map.Entry<String, List<FileNameTree>> duplicatedName) {
    return "Update this feature name"
      + " \""
      + duplicatedName.getKey()
      + "\" that is already defined in: "
      + duplicatedName.getValue()
      .stream()
      .map(o -> o.getFile().getName())
      .distinct()
      .sorted()
      .collect(Collectors.joining(", "));
  }

  private boolean isFeatureNameDuplicated(List<FileNameTree> fileNameTrees) {
    return fileNameTrees.stream()
      .map(f -> f.getFile().getAbsolutePath())
      .distinct()
      .count() > 1;
  }

}
