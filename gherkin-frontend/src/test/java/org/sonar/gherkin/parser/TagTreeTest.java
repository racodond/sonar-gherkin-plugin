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
import org.sonar.plugins.gherkin.api.tree.TagTree;

import static org.fest.assertions.Assertions.assertThat;

public class TagTreeTest extends GherkinTreeTest {

  public TagTreeTest() {
    super(GherkinLexicalGrammar.TAG);
  }

  @Test
  public void tag() throws Exception {
    checkParsed("@mytag", "mytag");
    checkParsed("@my-tag ", "my-tag");
    checkParsed(" @my-tag ", "my-tag");
  }

  @Test
  public void notTag() throws Exception {
    checkNotParsed("@");
    checkNotParsed("@@mytag");
    checkNotParsed("mytag");
  }

  private void checkParsed(String toParse, String tag) {
    TagTree tree = (TagTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.text()).isEqualTo(tag);
  }

}
