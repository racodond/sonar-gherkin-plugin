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
package org.sonar.gherkin.parser;

import org.junit.Test;
import org.sonar.plugins.gherkin.api.tree.ScenarioTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScenarioTreeTest extends GherkinTreeTest {

  public ScenarioTreeTest() {
    super(GherkinLexicalGrammar.SCENARIO);
  }

  @Test
  public void scenario() throws Exception {
    ScenarioTree tree;

    tree = checkParsed("Scenario:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNull();

    tree = checkParsed("Scenario: name...");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");

    tree = checkParsed("@mytag @my-tag\nScenario: name...");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");

    tree = checkParsed("@mytag @my-tag\nScenario: name...\nblabal...\nblabla...");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");

    tree = checkParsed("@mytag @my-tag\nScenario: name...\nGiven ...\nWhen ...");
    assertThat(tree.steps()).hasSize(2);
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
  }

  @Test
  public void notScenario() throws Exception {
    checkNotParsed("Scenario");
    checkNotParsed("Scenario : name...");
  }

  private ScenarioTree checkParsed(String toParse) {
    ScenarioTree tree = (ScenarioTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.steps()).isNotNull();
    assertThat(tree.tags()).isNotNull();
    return tree;
  }

}
