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
package org.sonar.gherkin.its;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class MetricsTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "metrics";
  private static Sonar wsClient;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/metrics/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setProfile("empty-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);

    wsClient = orchestrator.getServer().getWsClient();
  }

  @Test
  public void project_measures() {
    assertThat(getProjectMeasure("lines").getIntValue()).isEqualTo(39);
    assertThat(getProjectMeasure("ncloc").getIntValue()).isEqualTo(26);
    assertThat(getProjectMeasure("classes").getIntValue()).isEqualTo(2);
    assertThat(getProjectMeasure("functions").getIntValue()).isEqualTo(5);
    assertThat(getProjectMeasure("statements").getIntValue()).isEqualTo(15);
    assertThat(getProjectMeasure("files").getIntValue()).isEqualTo(2);
    assertThat(getProjectMeasure("directories").getIntValue()).isEqualTo(2);

    assertThat(getProjectMeasure("comment_lines").getIntValue()).isEqualTo(4);
    assertThat(getProjectMeasure("comment_lines_density").getValue()).isEqualTo(13.3);

    assertThat(getProjectMeasure("complexity")).isNull();
    assertThat(getProjectMeasure("complexity_in_functions")).isNull();
    assertThat(getProjectMeasure("function_complexity")).isNull();
    assertThat(getProjectMeasure("function_complexity_distribution")).isNull();
    assertThat(getProjectMeasure("file_complexity")).isNull();

    assertThat(getProjectMeasure("duplicated_lines").getIntValue()).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_files").getIntValue()).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_blocks").getIntValue()).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_lines_density").getValue()).isEqualTo(0.0);

    assertThat(getProjectMeasure("violations").getIntValue()).isEqualTo(0);
  }

  @Test
  public void dir_measures() {
    assertThat(getDirMeasure("lines").getIntValue()).isEqualTo(22);
    assertThat(getDirMeasure("ncloc").getIntValue()).isEqualTo(15);
    assertThat(getDirMeasure("classes").getIntValue()).isEqualTo(1);
    assertThat(getDirMeasure("functions").getIntValue()).isEqualTo(3);
    assertThat(getDirMeasure("statements").getIntValue()).isEqualTo(9);
    assertThat(getDirMeasure("files").getIntValue()).isEqualTo(1);
    assertThat(getDirMeasure("directories").getIntValue()).isEqualTo(1);

    assertThat(getDirMeasure("comment_lines").getIntValue()).isEqualTo(2);
    assertThat(getDirMeasure("comment_lines_density").getValue()).isEqualTo(11.8);

    assertThat(getDirMeasure("complexity")).isNull();
    assertThat(getDirMeasure("complexity_in_functions")).isNull();
    assertThat(getDirMeasure("function_complexity")).isNull();
    assertThat(getDirMeasure("function_complexity_distribution")).isNull();
    assertThat(getDirMeasure("file_complexity")).isNull();

    assertThat(getDirMeasure("duplicated_lines").getIntValue()).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_files").getIntValue()).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_blocks").getIntValue()).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_lines_density").getValue()).isEqualTo(0.0);

    assertThat(getDirMeasure("violations").getIntValue()).isEqualTo(0);
  }

  @Test
  public void file1_measures() {
    assertThat(getFile1Measure("lines").getIntValue()).isEqualTo(17);
    assertThat(getFile1Measure("ncloc").getIntValue()).isEqualTo(11);
    assertThat(getFile1Measure("classes").getIntValue()).isEqualTo(1);
    assertThat(getFile1Measure("functions").getIntValue()).isEqualTo(2);
    assertThat(getFile1Measure("statements").getIntValue()).isEqualTo(6);

    assertThat(getFile1Measure("comment_lines").getIntValue()).isEqualTo(2);
    assertThat(getFile1Measure("comment_lines_density").getValue()).isEqualTo(15.4);

    assertThat(getFile1Measure("complexity")).isNull();
    assertThat(getFile1Measure("complexity_in_functions")).isNull();
    assertThat(getFile1Measure("function_complexity")).isNull();
    assertThat(getFile1Measure("function_complexity_distribution")).isNull();
    assertThat(getFile1Measure("file_complexity")).isNull();

    assertThat(getFile1Measure("duplicated_lines")).isNull();
    assertThat(getFile1Measure("duplicated_blocks")).isNull();
    assertThat(getFile1Measure("duplicated_lines_density")).isNull();

    assertThat(getFile1Measure("violations")).isNull();
  }

  @Test
  public void file2_measures() {
    assertThat(getFile2Measure("lines").getIntValue()).isEqualTo(22);
    assertThat(getFile2Measure("ncloc").getIntValue()).isEqualTo(15);
    assertThat(getFile2Measure("classes").getIntValue()).isEqualTo(1);
    assertThat(getFile2Measure("functions").getIntValue()).isEqualTo(3);
    assertThat(getFile2Measure("statements").getIntValue()).isEqualTo(9);

    assertThat(getFile2Measure("comment_lines").getIntValue()).isEqualTo(2);
    assertThat(getFile2Measure("comment_lines_density").getValue()).isEqualTo(11.8);

    assertThat(getFile2Measure("complexity")).isNull();
    assertThat(getFile2Measure("complexity_in_functions")).isNull();
    assertThat(getFile2Measure("function_complexity")).isNull();
    assertThat(getFile2Measure("function_complexity_distribution")).isNull();
    assertThat(getFile2Measure("file_complexity")).isNull();

    assertThat(getFile2Measure("duplicated_lines")).isNull();
    assertThat(getFile2Measure("duplicated_blocks")).isNull();
    assertThat(getFile2Measure("duplicated_lines_density")).isNull();

    assertThat(getFile2Measure("violations")).isNull();
  }

  private Measure getProjectMeasure(String metricKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(PROJECT_KEY, metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getDirMeasure(String metricKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(PROJECT_KEY + ":src/dir", metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getFileMeasure(String metricKey, String fileKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(fileKey, metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getFile1Measure(String metricKey) {
    return getFileMeasure(metricKey, PROJECT_KEY + ":src/file1.feature");
  }

  private Measure getFile2Measure(String metricKey) {
    return getFileMeasure(metricKey, PROJECT_KEY + ":src/dir/file2.feature");
  }

}
