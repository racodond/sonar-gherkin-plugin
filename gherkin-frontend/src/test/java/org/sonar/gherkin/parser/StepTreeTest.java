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
import org.sonar.plugins.gherkin.api.tree.StepTree;

import static org.fest.assertions.Assertions.assertThat;

public class StepTreeTest extends GherkinTreeTest {

  public StepTreeTest() {
    super(GherkinLexicalGrammar.STEP);
  }

  @Test
  public void step() throws Exception {
    StepTree tree;

    checkParsed("Given I am a customer", "Given", "I am a customer");
    checkParsed(" Given I am a customer", "Given", "I am a customer");
    checkParsed(" Given I am a customer ", "Given", "I am a customer ");

    checkParsed("When I add a product to my cart", "When", "I add a product to my cart");
    checkParsed(" When I add a product to my cart", "When", "I add a product to my cart");
    checkParsed(" When I add a product to my cart ", "When", "I add a product to my cart ");

    checkParsed("Then I should see the product in my cart", "Then", "I should see the product in my cart");
    checkParsed(" Then I should see the product in my cart", "Then", "I should see the product in my cart");
    checkParsed(" Then I should see the product in my cart ", "Then", "I should see the product in my cart ");

    checkParsed("* I should see the product in my cart", "*", "I should see the product in my cart");
    checkParsed(" * I should see the product in my cart", "*", "I should see the product in my cart");
    checkParsed(" * I should see the product in my cart ", "*", "I should see the product in my cart ");

    tree = checkParsed("Given I am a customer:\n\"\"\"\nblabla...\nblabla...\n\"\"\"", "Given", "I am a customer:");
    assertThat(tree.table()).isNull();
    assertThat(tree.docString()).isNotNull();
    assertThat(tree.docString().data()).hasSize(2);

    tree = checkParsed("Given I am a customer:\n| abc |\n|2|", "Given", "I am a customer:");
    assertThat(tree.table()).isNotNull();
    assertThat(tree.docString()).isNull();
    assertThat(tree.table().rows()).hasSize(2);

    tree = checkParsed("Given a < b", "Given", "a < b");
    assertThat(tree.variables()).hasSize(0);

    tree = checkParsed("Given blabla... <abc>zzz blabla...<def>", "Given", "blabla... <abc>zzz blabla...<def>");
    assertThat(tree.variables()).hasSize(2);
    assertThat(tree.variables().contains("abc")).isTrue();
    assertThat(tree.variables().contains("def")).isTrue();
    assertThat(tree.variables().contains("zzz")).isFalse();
  }

  @Test
  public void notStep() throws Exception {
    checkNotParsed("blabla...");
    checkNotParsed("GIVEN I am a customer");
    checkNotParsed("given I am a customer");
    checkNotParsed("WHEN I add a product to my cart");
    checkNotParsed("when I add a product to my cart");
    checkNotParsed("THEN I should see the product in my cart");
    checkNotParsed("then I should see the product in my cart");
  }

  private StepTree checkParsed(String toParse, String prefix, String sentence) {
    StepTree tree = (StepTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.sentence()).isNotNull();
    assertThat(tree.prefix().keyword()).isNotNull();
    assertThat(tree.prefix().text()).isNotNull();
    assertThat(tree.variables()).isNotNull();
    assertThat(tree.prefix().text()).isEqualTo(prefix);
    assertThat(tree.sentence().text()).isEqualTo(sentence);
    return tree;
  }

}
