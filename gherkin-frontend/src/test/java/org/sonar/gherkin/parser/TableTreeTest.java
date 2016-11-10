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
import org.sonar.plugins.gherkin.api.tree.TableTree;

import static org.fest.assertions.Assertions.assertThat;

public class TableTreeTest extends GherkinTreeTest {

  public TableTreeTest() {
    super(GherkinLexicalGrammar.TABLE);
  }

  @Test
  public void table() throws Exception {
    TableTree tree;

    tree = checkParsed("||");
    assertThat(tree.rows()).hasSize(1);

    tree = checkParsed(" ||");
    assertThat(tree.rows()).hasSize(1);

    tree = checkParsed(" | |");
    assertThat(tree.rows()).hasSize(1);

    tree = checkParsed("| test|");
    assertThat(tree.rows()).hasSize(1);

    tree = checkParsed("| test| \n | 2 |");
    assertThat(tree.rows()).hasSize(2);

    tree = checkParsed("| test| \n | 2 | \n | sqfqsf|");
    assertThat(tree.rows()).hasSize(3);
  }

  @Test
  public void notTable() throws Exception {
    checkNotParsed("abc");
    checkNotParsed("|abc");
    checkNotParsed("| abc");
  }

  private TableTree checkParsed(String toParse) {
    TableTree tree = (TableTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.rows()).isNotNull();
    return tree;
  }

}
