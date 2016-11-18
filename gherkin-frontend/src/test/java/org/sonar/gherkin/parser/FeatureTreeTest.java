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
package org.sonar.gherkin.parser;

import org.junit.Test;
import org.sonar.plugins.gherkin.api.tree.FeatureTree;

import static org.fest.assertions.Assertions.assertThat;

public class FeatureTreeTest extends GherkinTreeTest {

  public FeatureTreeTest() {
    super(GherkinLexicalGrammar.FEATURE);
  }

  @Test
  public void feature() throws Exception {
    FeatureTree tree;

    tree = checkParsed("Feature: my feature");
    assertThat(tree.declaration().name().text()).isEqualTo("my feature");
    assertThat(tree.declaration().tags()).hasSize(0);
    assertThat(tree.declaration().description()).isNull();
    assertThat(tree.background()).isNull();
    assertThat(tree.scenarioOutlines()).hasSize(0);
    assertThat(tree.scenarios()).hasSize(0);

    tree = checkParsed("Feature: my feature\nblabla...\nblabla...");
    assertThat(tree.declaration().name().text()).isEqualTo("my feature");
    assertThat(tree.declaration().tags()).hasSize(0);
    assertThat(tree.declaration().description()).isNotNull();
    assertThat(tree.background()).isNull();
    assertThat(tree.scenarioOutlines()).hasSize(0);
    assertThat(tree.scenarios()).hasSize(0);

    tree = checkParsed("@mytag @my-tag\nFeature: my feature\nblabla...\nblabla...");
    assertThat(tree.declaration().name().text()).isEqualTo("my feature");
    assertThat(tree.declaration().tags()).hasSize(2);
    assertThat(tree.declaration().description()).isNotNull();
    assertThat(tree.background()).isNull();
    assertThat(tree.scenarioOutlines()).hasSize(0);
    assertThat(tree.scenarios()).hasSize(0);

    tree = checkParsed(
      "@mytag @my-tag\n" +
        "Feature:my feature\n" +
        "blabla...\n" +
        "blabla...\n" +
        "Background:");
    assertThat(tree.declaration().name().text()).isEqualTo("my feature");
    assertThat(tree.declaration().tags()).hasSize(2);
    assertThat(tree.declaration().description()).isNotNull();
    assertThat(tree.background()).isNotNull();
    assertThat(tree.scenarioOutlines()).hasSize(0);
    assertThat(tree.scenarios()).hasSize(0);

    tree = checkParsed(
      "@mytag @my-tag\n" +
        "Feature:my feature\n" +
        "blabla...\n" +
        "blabla...\n" +
        "Background:\n" +
        "Given ...\n" +
        "And ...\n" +
        "\n" +
        "Scenario: name1\n" +
        "Given ...\n" +
        "Scenario: name2\n" +
        "Given ...\n" +
        "And ...\n" +
        "When ...\n" +
        "Then ...\n" +
        "Scenario Outline: name3\n" +
        "* ...\n" +
        "When ...\n" +
        "Then ...\n" +
        "\n" +
        "@mytag\n" +
        "Examples:\n" +
        "|d1|\n");
    assertThat(tree.declaration().name().text()).isEqualTo("my feature");
    assertThat(tree.declaration().tags()).hasSize(2);
    assertThat(tree.declaration().description()).isNotNull();
    assertThat(tree.background()).isNotNull();
    assertThat(tree.scenarioOutlines()).hasSize(1);
    assertThat(tree.scenarios()).hasSize(2);
  }

  @Test
  public void notFeature() throws Exception {
    checkNotParsed("feature");
    checkNotParsed("Feature");
    checkNotParsed("Feature:");
  }

  private FeatureTree checkParsed(String toParse) {
    FeatureTree tree = (FeatureTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.declaration()).isNotNull();
    assertThat(tree.scenarios()).isNotNull();
    assertThat(tree.scenarioOutlines()).isNotNull();
    return tree;
  }

}
