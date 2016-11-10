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
import org.sonar.plugins.gherkin.api.tree.DocStringTree;
import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocStringTreeImpl extends GherkinTree implements DocStringTree {

  private final SyntaxToken prefix;
  private final SyntaxToken suffix;
  private final SyntaxToken contentType;
  private final List<SyntaxToken> data;

  public DocStringTreeImpl(SyntaxToken prefix, @Nullable SyntaxToken contentType, @Nullable List<SyntaxToken> data, SyntaxToken suffix) {
    this.prefix = prefix;
    this.contentType = contentType;

    if (data != null) {
      this.data = data;
    } else {
      this.data = new ArrayList<>();
    }

    this.suffix = suffix;
  }

  @Override
  public Kind getKind() {
    return Kind.DOC_STRING;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(prefix, contentType),
      data.iterator(),
      Iterators.singletonIterator(suffix));
  }

  @Override
  public List<SyntaxToken> data() {
    return data;
  }

  @Override
  public SyntaxToken prefix() {
    return prefix;
  }

  @Override
  public SyntaxToken suffix() {
    return suffix;
  }

  @Override
  @Nullable
  public SyntaxToken contentType() {
    return contentType;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitDocString(this);
  }

}
