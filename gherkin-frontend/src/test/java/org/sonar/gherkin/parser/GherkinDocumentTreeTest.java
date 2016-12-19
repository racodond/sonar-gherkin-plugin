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

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class GherkinDocumentTreeTest extends GherkinTreeTest {

  public GherkinDocumentTreeTest() {
    super(GherkinLexicalGrammar.GHERKIN_DOCUMENT);
  }

  @Test
  public void gherkinDocument() throws Exception {
    GherkinDocumentTree tree;

    tree = checkParsed(new File("src/test/resources/parser/empty.feature"));
    assertThat(tree.feature()).isNull();
    assertThat(tree.languageDeclaration()).isNull();
    assertThat(tree.language()).isEqualTo("en");

    tree = checkParsed(new File("src/test/resources/parser/parse.feature"));
    assertThat(tree.hasByteOrderMark()).isEqualTo(false);
    assertThat(tree.feature()).isNotNull();
    assertThat(tree.feature().scenarioOutlines()).hasSize(1);
    assertThat(tree.feature().scenarios()).hasSize(2);
    assertThat(tree.feature().background()).isNotNull();
    assertThat(tree.languageDeclaration()).isNull();
    assertThat(tree.language()).isEqualTo("en");

    tree = checkParsed(new File("src/test/resources/parser/parse-bom.feature"));
    assertThat(tree.hasByteOrderMark()).isEqualTo(true);
    assertThat(tree.feature()).isNotNull();
    assertThat(tree.feature().scenarioOutlines()).hasSize(1);
    assertThat(tree.feature().scenarios()).hasSize(2);
    assertThat(tree.feature().background()).isNotNull();
    assertThat(tree.languageDeclaration()).isNull();
    assertThat(tree.language()).isEqualTo("en");
  }

  @Test
  public void notGherkinDocument() throws Exception {
    checkNotParsed(new File("src/test/resources/parser/two-backgrounds.feature"));
  }

  private GherkinDocumentTree checkParsed(File file) throws Exception {
    GherkinDocumentTree tree = (GherkinDocumentTree) parser().parse(Files.toString(file, Charsets.UTF_8));
    assertThat(tree).isNotNull();
    assertThat(tree.language()).isNotNull();
    return tree;
  }

}
