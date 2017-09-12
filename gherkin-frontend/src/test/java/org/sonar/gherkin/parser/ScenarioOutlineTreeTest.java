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
import org.sonar.plugins.gherkin.api.tree.ScenarioOutlineTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScenarioOutlineTreeTest extends GherkinTreeTest {

  public ScenarioOutlineTreeTest() {
    super(GherkinLexicalGrammar.SCENARIO_OUTLINE);
  }

  @Test
  public void scenarioOutline() throws Exception {
    ScenarioOutlineTree tree;

    tree = checkParsed("Scenario Outline:\nExamples:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNull();
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table()).isNull();

    tree = checkParsed("Scenario Outline: name...\nExamples:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table()).isNull();

    tree = checkParsed("Scenario Outline: name...\nExamples:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table()).isNull();

    tree = checkParsed("Scenario Outline: name...\nblabla...\nExamples:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table()).isNull();

    tree = checkParsed("Scenario Outline: name...\nblabla...\nGiven blabla...\n\nExamples:");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table()).isNull();

    tree = checkParsed("Scenario Outline: name...\nblabla...\nGiven blabla...\n\nExamples:\n|d1|d2|");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table().rows()).hasSize(1);

    tree = checkParsed("@mytag\nScenario Outline: name...\nblabla...\nGiven blabla...\n\nExamples:\n|d1|d2|");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.tags()).hasSize(1);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table().rows()).hasSize(1);

    tree = checkParsed("@mytag\nScenario Outline: name...\nblabla...\nGiven blabla...\n\n@mytag\nExamples:\n|d1|d2|");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.tags()).hasSize(1);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(1);
    assertThat(tree.examples().get(0).table().rows()).hasSize(1);
    assertThat(tree.examples().get(0).tags()).hasSize(1);

    tree = checkParsed("@mytag\nScenario Outline: name...\nblabla...\nGiven blabla...\n\n@mytag\nExamples:\n|d1|d2|\n@mytag @mytag1\nExamples:\n|d1|d2|\n|d1|d2|");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.tags()).hasSize(1);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("name...");
    assertThat(tree.examples()).hasSize(2);
    assertThat(tree.examples().get(0).table().rows()).hasSize(1);
    assertThat(tree.examples().get(0).tags()).hasSize(1);
    assertThat(tree.examples().get(1).table().rows()).hasSize(2);
    assertThat(tree.examples().get(1).tags()).hasSize(2);
  }

  @Test
  public void notScenarioOutline() throws Exception {
    checkNotParsed("Scenario");
    checkNotParsed("Scenario Outline");
    checkNotParsed("Scenario Outline: name...");
  }

  private ScenarioOutlineTree checkParsed(String toParse) {
    ScenarioOutlineTree tree = (ScenarioOutlineTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.steps()).isNotNull();
    assertThat(tree.tags()).isNotNull();
    assertThat(tree.examples()).isNotNull();
    return tree;
  }

}
