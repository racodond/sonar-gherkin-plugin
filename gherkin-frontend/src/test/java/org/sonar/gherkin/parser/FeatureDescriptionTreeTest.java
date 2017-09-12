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
import org.sonar.plugins.gherkin.api.tree.DescriptionTree;

import static org.fest.assertions.Assertions.assertThat;

public class FeatureDescriptionTreeTest extends GherkinTreeTest {

  public FeatureDescriptionTreeTest() {
    super(GherkinLexicalGrammar.FEATURE_DESCRIPTION);
  }

  @Test
  public void featureDescription() throws Exception {
    checkParsed("blabla...", 1);
    checkParsed("blabla...\nblabla...", 2);
    checkParsed("blabla...\n\nblabla...", 2);

    checkParsed("Given ...", 1);
    checkParsed("When ...", 1);
    checkParsed("Then ...", 1);
    checkParsed("But ...", 1);
    checkParsed("And ...", 1);
    checkParsed("* ...", 1);
    checkParsed("Examples ...", 1);
    checkParsed("\"\"\" ...", 1);
    checkParsed("| ...", 1);
  }

  @Test
  public void notFeatureDescription() throws Exception {
    checkNotParsed("@ ...");
    checkNotParsed("Background ...");
    checkNotParsed("Scenario ...");
    checkNotParsed("Scenario Outline ...");
  }

  private void checkParsed(String toParse, int size) {
    DescriptionTree tree = (DescriptionTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.descriptionLines()).isNotNull();
    assertThat(tree.descriptionLines().size()).isEqualTo(size);
  }

}
