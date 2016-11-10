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
package org.sonar.gherkin.its;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueClient;
import org.sonar.wsclient.issue.IssueQuery;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CustomRulesTest {

  @org.junit.ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "custom-rules";
  private static IssueClient issueClient;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/custom-rules/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setProfile("gherkin-custom-rules-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);

    issueClient = orchestrator.getServer().wsClient().issueClient();
  }

  @Test
  public void issues_against_rule_forbidden_tag() {
    List<Issue> issues = issueClient.find(IssueQuery.create().rules("custom-gherkin:forbidden-tag")).list();

    assertThat(issues).hasSize(1);

    Issue issue = issues.get(0);
    assertThat(issue.line()).isEqualTo(1);
    assertThat(issue.message()).isEqualTo("Remove this usage of the forbidden \"bar\" tag.");
    assertThat(issue.debt()).isEqualTo("5min");
    assertThat(issue.severity()).isEqualTo("MAJOR");
  }

  @Test
  public void issues_against_rule_forbidden_name_content() {
    List<Issue> issues = issueClient.find(IssueQuery.create().rules("custom-gherkin:forbidden-name-content")).list();

    assertThat(issues).hasSize(1);

    Issue issue = issues.get(0);
    assertThat(issue.line()).isEqualTo(8);
    assertThat(issue.message()).isEqualTo("Remove this usage of \"WTF\".");
    assertThat(issue.debt()).isEqualTo("5min");
    assertThat(issue.severity()).isEqualTo("CRITICAL");
  }

}
