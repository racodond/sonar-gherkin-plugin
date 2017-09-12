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

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.sonar.gherkin.parser.GherkinParserBuilder;
import org.sonar.gherkin.tree.impl.InternalSyntaxToken;
import org.sonar.gherkin.tree.impl.NameTreeImpl;
import org.sonar.gherkin.visitors.GherkinVisitorContext;
import org.sonar.plugins.gherkin.api.GherkinCheck;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.tree.NameTree;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatedScenarioNamesCheckTest {

  private static final String TEST_DIRECTORY = "duplicated-scenario-names/";

  @Test
  public void analyze_one_single_file() {
    DuplicatedScenarioNamesCheck check = new DuplicatedScenarioNamesCheck();
    scanFile(check, "scenarios.feature");

    Assert.assertNotNull(check.getNames());
    Assert.assertEquals(3, check.getNames().size());

    Assert.assertTrue(check.getNames().containsKey("Background name blabla"));
    Assert.assertTrue(check.getNames().containsKey("Scenario name blabla"));
    Assert.assertTrue(check.getNames().containsKey("Scenario outline name blabla"));

    Assert.assertNotNull(check.getNames().get("Background name blabla"));
    Assert.assertNotNull(check.getNames().get("Scenario name blabla"));
    Assert.assertNotNull(check.getNames().get("Scenario outline name blabla"));

    Assert.assertEquals(1, check.getNames().get("Background name blabla").size());
    Assert.assertEquals(2, check.getNames().get("Scenario name blabla").size());
    Assert.assertEquals(1, check.getNames().get("Scenario outline name blabla").size());

    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Background name blabla").get(0).getFile().getPath());
    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Scenario name blabla").get(0).getFile().getPath());
    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Scenario outline name blabla").get(0).getFile().getPath());
  }

  @Test
  public void analyze_several_files() {
    DuplicatedScenarioNamesCheck check = new DuplicatedScenarioNamesCheck();

    Map<String, List<FileNameTree>> names = new HashMap<>();

    NameTree nameTree1 = new NameTreeImpl(new InternalSyntaxToken(2, 1, "Background name blabla", new ArrayList<>(), false, false));
    NameTree nameTree2 = new NameTreeImpl(new InternalSyntaxToken(4, 1, "Scenario name blabla", new ArrayList<>(), false, false));

    names.put("Background name blabla", Lists.newArrayList(new FileNameTree(getTestFile("feature2.feature"), nameTree1)));
    names.put("abc", Lists.newArrayList(new FileNameTree(getTestFile("feature2.feature"), nameTree2)));
    check.setNames(names);

    scanFile(check, "scenarios.feature");

    Assert.assertNotNull(check.getNames());
    Assert.assertEquals(4, check.getNames().size());

    Assert.assertTrue(check.getNames().containsKey("Background name blabla"));
    Assert.assertTrue(check.getNames().containsKey("Scenario name blabla"));
    Assert.assertTrue(check.getNames().containsKey("Scenario outline name blabla"));
    Assert.assertTrue(check.getNames().containsKey("abc"));

    Assert.assertNotNull(check.getNames().get("Background name blabla"));
    Assert.assertNotNull(check.getNames().get("Scenario name blabla"));
    Assert.assertNotNull(check.getNames().get("Scenario outline name blabla"));
    Assert.assertNotNull(check.getNames().get("abc"));

    Assert.assertEquals(2, check.getNames().get("Background name blabla").size());
    Assert.assertEquals(2, check.getNames().get("Scenario name blabla").size());
    Assert.assertEquals(1, check.getNames().get("Scenario outline name blabla").size());
    Assert.assertEquals(1, check.getNames().get("abc").size());

    Assert.assertEquals(getTestFilePath("feature2.feature"), check.getNames().get("Background name blabla").get(0).getFile().getPath());
    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Background name blabla").get(1).getFile().getPath());

    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Scenario name blabla").get(0).getFile().getPath());
    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Scenario name blabla").get(1).getFile().getPath());

    Assert.assertEquals(getTestFilePath("scenarios.feature"), check.getNames().get("Scenario outline name blabla").get(0).getFile().getPath());

    Assert.assertEquals(getTestFilePath("feature2.feature"), check.getNames().get("abc").get(0).getFile().getPath());
  }

  private void scanFile(GherkinCheck check, String fileName) {
    GherkinDocumentTree gherkinDocument = (GherkinDocumentTree) GherkinParserBuilder
      .createTestParser(Charsets.UTF_8)
      .parse(getTestFile(fileName));

    GherkinVisitorContext context = new GherkinVisitorContext(gherkinDocument, getTestFile(fileName));

    check.scanFile(context);
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getTestFile(TEST_DIRECTORY + fileName);
  }

  private String getTestFilePath(String fileName) {
    return CheckTestUtils.getTestFile(TEST_DIRECTORY + fileName).getPath();
  }

}
