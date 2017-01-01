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
import org.sonar.plugins.gherkin.api.tree.DocStringTree;

import static org.fest.assertions.Assertions.assertThat;

public class DocStringTreeTest extends GherkinTreeTest {

  public DocStringTreeTest() {
    super(GherkinLexicalGrammar.DOC_STRING);
  }

  @Test
  public void docString() throws Exception {
    DocStringTree tree;

    tree = checkParsed("\"\"\"\n\"\"\"");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.prefix().text()).isEqualTo("\"\"\"");
    assertThat(tree.suffix().text()).isEqualTo("\"\"\"");
    assertThat(tree.data()).hasSize(0);

    tree = checkParsed(" \"\"\"\n \"\"\"");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.data()).hasSize(0);

    tree = checkParsed("\"\"\"\nblabla\n\"\"\"");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.data()).hasSize(1);

    tree = checkParsed("\"\"\"\nblabla\nblabla\n\"\"\"");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.data()).hasSize(2);

    tree = checkParsed("\"\"\"\n blabla\n  blabla\n\"\"\"");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.data()).hasSize(2);

    tree = checkParsed("\"\"\"type\n blabla\n  blabla\n\"\"\"");
    assertThat(tree.contentType()).isNotNull();
    assertThat(tree.contentType().text()).isEqualTo("type");
    assertThat(tree.data()).hasSize(2);

    tree = checkParsed("\"\"\"type\n # blabla\n  blabla\n\"\"\"");
    assertThat(tree.contentType()).isNotNull();
    assertThat(tree.contentType().text()).isEqualTo("type");
    assertThat(tree.data()).hasSize(2);

    tree = checkParsed("```\n```");
    assertThat(tree.contentType()).isNull();
    assertThat(tree.prefix().text()).isEqualTo("```");
    assertThat(tree.suffix().text()).isEqualTo("```");

    tree = checkParsed("```string\nblabla...\nblabla...\n```");
    assertThat(tree.contentType()).isNotNull();
    assertThat(tree.contentType().text()).isEqualTo("string");
    assertThat(tree.data()).hasSize(2);
    assertThat(tree.prefix().text()).isEqualTo("```");
    assertThat(tree.suffix().text()).isEqualTo("```");
  }

  @Test
  public void notDocString() throws Exception {
    checkNotParsed("\"\"");
    checkNotParsed("\"\"\"\"\"\"");
  }

  private DocStringTree checkParsed(String toParse) {
    DocStringTree tree = (DocStringTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.suffix()).isNotNull();
    assertThat(tree.data()).isNotNull();
    return tree;
  }

}
