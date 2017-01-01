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
import org.sonar.plugins.gherkin.api.tree.FeatureDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class FeatureDeclarationTreeTest extends GherkinTreeTest {

  public FeatureDeclarationTreeTest() {
    super(GherkinLexicalGrammar.FEATURE_DECLARATION);
  }

  @Test
  public void featureDeclaration() throws Exception {
    FeatureDeclarationTree tree;

    tree = checkParsed("Feature:");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNull();

    tree = checkParsed("Feature: \n\n");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNull();

    tree = checkParsed("Feature: my feature");
    assertThat(tree.name()).isNotNull();
    assertThat(tree.name().text()).isEqualTo("my feature");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNull();

    tree = checkParsed("Feature: my feature\nblabla...\nblabla...");
    assertThat(tree.name().text()).isEqualTo("my feature");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.description().descriptionLines()).hasSize(2);

    tree = checkParsed("@mytag @my-tag\nFeature: my feature\nblabla...\nblabla...");
    assertThat(tree.name().text()).isEqualTo("my feature");
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNotNull();
  }

  @Test
  public void notFeatureDeclaration() throws Exception {
    checkNotParsed("feature");
    checkNotParsed("Feature");
  }

  private FeatureDeclarationTree checkParsed(String toParse) {
    FeatureDeclarationTree tree = (FeatureDeclarationTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.tags()).isNotNull();
    return tree;
  }

}
