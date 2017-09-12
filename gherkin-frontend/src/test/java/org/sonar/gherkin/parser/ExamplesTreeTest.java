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
package org.sonar.gherkin.parser;

import org.junit.Test;
import org.sonar.plugins.gherkin.api.tree.ExamplesTree;

import static org.fest.assertions.Assertions.assertThat;

public class ExamplesTreeTest extends GherkinTreeTest {

  public ExamplesTreeTest() {
    super(GherkinLexicalGrammar.EXAMPLES);
  }

  @Test
  public void examples() throws Exception {
    ExamplesTree tree;

    tree = checkParsed("Examples:");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("Examples: blabla...");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNotNull();
    assertThat(tree.description()).isNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("Examples: blabla...\nblabla...");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNotNull();
    assertThat(tree.description()).isNotNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("Examples:\nblabla...");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNotNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("Examples: \nblabla...");
    assertThat(tree.tags()).hasSize(0);
    assertThat(tree.name()).isNull();
    assertThat(tree.description()).isNotNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("@mytag @my-tag\nExamples:");
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("@mytag @my-tag\nExamples:\nblabla...\nblabla...");
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.table()).isNull();

    tree = checkParsed("@mytag @my-tag\nExamples:\nblabla...\nblabla...\n|d1|d2|");
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNotNull();
    assertThat(tree.table().rows()).hasSize(1);

    tree = checkParsed("@mytag @my-tag\nExamples:\n|d1|d2|\n|1|2|");
    assertThat(tree.tags()).hasSize(2);
    assertThat(tree.description()).isNull();
    assertThat(tree.table().rows()).hasSize(2);
  }

  @Test
  public void notExamples() throws Exception {
    checkNotParsed("Examples");
    checkNotParsed("Examples :");
    checkNotParsed("Examples :\n|aa");
  }

  private ExamplesTree checkParsed(String toParse) {
    ExamplesTree tree = (ExamplesTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.tags()).isNotNull();
    return tree;
  }

}
