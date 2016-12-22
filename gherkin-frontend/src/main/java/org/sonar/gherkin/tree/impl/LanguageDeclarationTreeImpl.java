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
package org.sonar.gherkin.tree.impl;

import com.google.common.collect.Iterators;
import org.sonar.gherkin.parser.GherkinDialectProvider;
import org.sonar.plugins.gherkin.api.tree.LanguageDeclarationTree;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.regex.Matcher;

public class LanguageDeclarationTreeImpl extends GherkinTree implements LanguageDeclarationTree {

  private final SyntaxToken languageDeclaration;

  public LanguageDeclarationTreeImpl(SyntaxToken languageDeclaration) {
    this.languageDeclaration = languageDeclaration;
  }

  @Override
  public Kind getKind() {
    return Kind.LANGUAGE_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(languageDeclaration);
  }

  @Override
  public String language() {
    Matcher matcher = GherkinDialectProvider.LANGUAGE_DECLARATION_PATTERN.matcher(languageDeclaration.text());
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new IllegalStateException("Expected language to be explicitly defined in " + languageDeclaration.text());
  }

  @Override
  public SyntaxToken value() {
    return languageDeclaration;
  }

  @Override
  public String text() {
    return languageDeclaration.text();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLanguageDeclaration(this);
  }

}
