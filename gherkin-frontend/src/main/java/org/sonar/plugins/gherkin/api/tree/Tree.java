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
package org.sonar.plugins.gherkin.api.tree;

import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;
import java.util.Iterator;

public interface Tree {

  boolean is(Kind... kind);

  void accept(DoubleDispatchVisitor visitor);

  Iterator<Tree> childrenIterator();

  @Nullable
  Tree parent();

  void setParent(Tree parent);

  boolean isLeaf();

  Kind getKind();

  enum Kind implements GrammarRuleKey {
    GHERKIN_DOCUMENT(GherkinDocumentTree.class),
    FEATURE(FeatureTree.class),
    FEATURE_DECLARATION(FeatureDeclarationTree.class),
    BACKGROUND(BackgroundTree.class),
    SCENARIO(ScenarioTree.class),
    SCENARIO_OUTLINE(ScenarioOutlineTree.class),
    EXAMPLES(ExamplesTree.class),
    STEP(StepTree.class),
    FEATURE_PREFIX(FeaturePrefixTree.class),
    BACKGROUND_PREFIX(BackgroundPrefixTree.class),
    SCENARIO_PREFIX(ScenarioPrefixTree.class),
    SCENARIO_OUTLINE_PREFIX(ScenarioOutlinePrefixTree.class),
    EXAMPLES_PREFIX(ExamplesPrefixTree.class),
    STEP_PREFIX(StepPrefixTree.class),
    TAG(TagTree.class),
    STEP_SENTENCE(StepSentenceTree.class),
    NAME(NameTree.class),
    DESCRIPTION(DescriptionTree.class),
    TABLE(TableTree.class),
    DOC_STRING(DocStringTree.class),
    LANGUAGE_DECLARATION(LanguageDeclarationTree.class),
    TOKEN(SyntaxToken.class),
    TRIVIA(SyntaxTrivia.class),
    SPACING(SyntaxSpacing.class);

    final Class<? extends Tree> associatedInterface;

    Kind(Class<? extends Tree> associatedInterface) {
      this.associatedInterface = associatedInterface;
    }

    public Class<? extends Tree> getAssociatedInterface() {
      return associatedInterface;
    }
  }

}
