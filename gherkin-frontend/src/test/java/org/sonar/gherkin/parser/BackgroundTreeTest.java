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
import org.sonar.plugins.gherkin.api.tree.BackgroundTree;

import static org.fest.assertions.Assertions.assertThat;

public class BackgroundTreeTest extends GherkinTreeTest {

  public BackgroundTreeTest() {
    super(GherkinLexicalGrammar.BACKGROUND);
  }

  @Test
  public void background() throws Exception {
    BackgroundTree tree;

    tree = checkParsed("Background:");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.description()).isNull();

    tree = checkParsed("Background: blabla...");
    assertThat(tree.steps()).hasSize(0);
    assertThat(tree.description()).isNotNull();

    tree = checkParsed("Background: blabla...\nGiven ...");
    assertThat(tree.steps()).hasSize(1);
    assertThat(tree.description()).isNotNull();

    tree = checkParsed("Background:\nGiven ...\nAnd ...");
    assertThat(tree.steps()).hasSize(2);
    assertThat(tree.description()).isNull();
  }

  @Test
  public void notBackground() throws Exception {
    checkNotParsed("Background");
    checkNotParsed("@mytag\nBackground:");
  }

  private BackgroundTree checkParsed(String toParse) {
    BackgroundTree tree = (BackgroundTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.steps()).isNotNull();
    return tree;
  }

}
