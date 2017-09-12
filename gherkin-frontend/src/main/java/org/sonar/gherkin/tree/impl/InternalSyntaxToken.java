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
package org.sonar.gherkin.tree.impl;

import org.sonar.plugins.gherkin.api.tree.SyntaxToken;
import org.sonar.plugins.gherkin.api.tree.SyntaxTrivia;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.List;

public class InternalSyntaxToken extends GherkinTree implements SyntaxToken {

  private final int line;
  private final int column;
  private final String value;
  private final boolean isEOF;
  private final boolean isBOM;
  private int endLine;
  private int endColumn;
  private final List<SyntaxTrivia> trivias;

  public InternalSyntaxToken(int line, int column, String value, List<SyntaxTrivia> trivias, boolean isEOF, boolean isBOM) {
    this.value = value;
    this.trivias = trivias;
    this.line = line;
    this.column = column;
    this.isEOF = isEOF;
    this.isBOM = isBOM;
    calculateEndOffsets();
  }

  private void calculateEndOffsets() {
    String[] lines = value.split("\r\n|\n|\r", -1);
    endColumn = column + value.length();
    endLine = line + lines.length - 1;

    if (endLine != line) {
      endColumn = lines[lines.length - 1].length();
    }
  }

  @Override
  public int endLine() {
    return endLine;
  }

  @Override
  public int endColumn() {
    return endColumn;
  }

  @Override
  public int line() {
    return line;
  }

  @Override
  public int column() {
    return column;
  }

  @Override
  public Kind getKind() {
    return Kind.TOKEN;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  public boolean isEOF() {
    return isEOF;
  }

  public boolean isBOM() {
    return isBOM;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitToken(this);
  }

  @Override
  public SyntaxToken getFirstToken() {
    return this;
  }

  @Override
  public SyntaxToken getLastToken() {
    return this;
  }

  @Override
  public String text() {
    return value;
  }

  @Override
  public List<SyntaxTrivia> trivias() {
    return trivias;
  }

}
