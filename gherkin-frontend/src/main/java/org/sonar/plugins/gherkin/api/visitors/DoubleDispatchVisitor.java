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
package org.sonar.plugins.gherkin.api.visitors;

import com.google.common.base.Preconditions;
import org.sonar.plugins.gherkin.api.tree.*;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public abstract class DoubleDispatchVisitor implements TreeVisitor {

  private TreeVisitorContext context = null;

  @Override
  public TreeVisitorContext getContext() {
    Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialised the context before accessing it");
    return context;
  }

  @Override
  public final void scanTree(TreeVisitorContext context) {
    this.context = context;
    scan(context.getTopTree());
  }

  protected void scan(@Nullable Tree tree) {
    if (tree != null) {
      tree.accept(this);
    }
  }

  protected void scanChildren(Tree tree) {
    Iterator<Tree> childrenIterator = tree.childrenIterator();
    Tree child;

    while (childrenIterator.hasNext()) {
      child = childrenIterator.next();
      if (child != null) {
        child.accept(this);
      }
    }
  }

  protected <T extends Tree> void scan(List<T> trees) {
    trees.forEach(this::scan);
  }

  public void visitGherkinDocument(GherkinDocumentTree tree) {
    scanChildren(tree);
  }

  public void visitFeature(FeatureTree tree) {
    scanChildren(tree);
  }

  public void visitFeatureDeclaration(FeatureDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitBackground(BackgroundTree tree) {
    scanChildren(tree);
  }

  public void visitScenario(ScenarioTree tree) {
    scanChildren(tree);
  }

  public void visitScenarioOutline(ScenarioOutlineTree tree) {
    scanChildren(tree);
  }

  public void visitExamples(ExamplesTree tree) {
    scanChildren(tree);
  }

  public void visitStep(StepTree tree) {
    scanChildren(tree);
  }

  public void visitDescription(DescriptionTree tree) {
    scanChildren(tree);
  }

  public void visitTag(TagTree tree) {
    scanChildren(tree);
  }

  public void visitName(NameTree tree) {
    scanChildren(tree);
  }

  public void visitStepSentence(StepSentenceTree tree) {
    scanChildren(tree);
  }

  public void visitFeaturePrefix(FeaturePrefixTree tree) {
    scanChildren(tree);
  }

  public void visitBackgroundPrefix(BackgroundPrefixTree tree) {
    scanChildren(tree);
  }

  public void visitScenarioPrefix(ScenarioPrefixTree tree) {
    scanChildren(tree);
  }

  public void visitScenarioOutlinePrefix(ScenarioOutlinePrefixTree tree) {
    scanChildren(tree);
  }

  public void visitExamplesPrefix(ExamplesPrefixTree tree) {
    scanChildren(tree);
  }

  public void visitStepPrefix(StepPrefixTree tree) {
    scanChildren(tree);
  }

  public void visitTable(TableTree tree) {
    scanChildren(tree);
  }

  public void visitDocString(DocStringTree tree) {
    scanChildren(tree);
  }

  public void visitToken(SyntaxToken token) {
    for (SyntaxTrivia syntaxTrivia : token.trivias()) {
      syntaxTrivia.accept(this);
    }
  }

  public void visitComment(SyntaxTrivia commentToken) {
    // no sub-tree
  }

}
