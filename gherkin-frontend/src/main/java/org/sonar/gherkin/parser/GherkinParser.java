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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.plugins.gherkin.api.tree.BasicScenarioTree;
import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Objects;

public class GherkinParser extends ActionParser<Tree> {

  public GherkinParser(Charset charset, LexerlessGrammarBuilder grammarBuilder, Class<GherkinGrammar> grammarClass,
                       TreeFactory treeFactory, GherkinNodeBuilder nodeBuilder, GrammarRuleKey rootRule) {
    super(charset, grammarBuilder, grammarClass, treeFactory, nodeBuilder, rootRule);
  }

  @Override
  public Tree parse(File file) {
    Tree tree = super.parse(file);
    addParentLink(tree);

    if (tree.is(Tree.Kind.GHERKIN_DOCUMENT)) {
      addStepType(tree, ((GherkinDocumentTree) tree).language());
    }

    return tree;
  }

  private static Tree addParentLink(Tree parent) {
    if (!parent.isLeaf()) {
      Lists.newArrayList(parent.childrenIterator())
        .stream()
        .filter(Objects::nonNull)
        .forEach(nextTree -> {
          nextTree.setParent(parent);
          addParentLink(nextTree);
        });
    }
    return parent;
  }

  private static Tree addStepType(Tree parent, String language) {
    if (!parent.isLeaf()) {
      Lists.newArrayList(parent.childrenIterator())
        .stream()
        .filter(Objects::nonNull)
        .forEach(nextTree -> {
          if (nextTree instanceof BasicScenarioTree) {
            ((BasicScenarioTree) nextTree).setStepTypes(language);
          }
          addStepType(nextTree, language);
        });
    }
    return parent;
  }

}
