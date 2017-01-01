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
package org.sonar.gherkin.tree.impl;

import com.google.common.collect.Iterators;
import org.sonar.gherkin.parser.GherkinDialectProvider;
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

public class GherkinDocumentTreeImpl extends GherkinTree implements GherkinDocumentTree {

  private final SyntaxToken byteOrderMark;
  private final LanguageDeclarationTree languageDeclaration;
  private final FeatureTree feature;
  private final SyntaxToken eof;

  public GherkinDocumentTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable LanguageDeclarationTree languageDeclaration, @Nullable FeatureTree feature, SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.languageDeclaration = languageDeclaration;
    this.feature = feature;
    this.eof = eof;
  }

  @Override
  public Kind getKind() {
    return Kind.GHERKIN_DOCUMENT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(byteOrderMark, languageDeclaration, feature, eof);
  }

  @Override
  public boolean hasByteOrderMark() {
    return byteOrderMark != null;
  }

  @Override
  public LanguageDeclarationTree languageDeclaration() {
    return languageDeclaration;
  }

  @Override
  public String language() {
    if (languageDeclaration == null) {
      return GherkinDialectProvider.DEFAULT_LANGUAGE;
    } else {
      return languageDeclaration.language();
    }
  }

  @Override
  @Nullable
  public FeatureTree feature() {
    return feature;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitGherkinDocument(this);
  }

}
