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
import org.sonar.plugins.gherkin.api.tree.*;
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepTreeImpl extends GherkinTree implements StepTree {

  private final PrefixTree prefix;
  private final StepSentenceTree sentence;
  private DocStringTree docString;
  private TableTree table;
  private Set<String> variables;
  private SemanticStepType semanticType;
  private SyntacticStepType syntacticType;

  public StepTreeImpl(PrefixTree prefix, StepSentenceTree sentence, @Nullable Tree data) {
    this.prefix = prefix;
    this.sentence = sentence;

    initData(data);
    initVariables();
  }

  @Override
  public Kind getKind() {
    return Kind.STEP;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(prefix, sentence, docString, table);
  }

  @Override
  public PrefixTree prefix() {
    return prefix;
  }

  @Override
  public StepSentenceTree sentence() {
    return sentence;
  }

  @Override
  public SyntacticStepType syntacticType() {
    return syntacticType;
  }

  @Override
  public SemanticStepType semanticType() {
    return semanticType;
  }

  @Override
  public void setSyntacticType(SyntacticStepType type) {
    this.syntacticType = type;
  }

  @Override
  public void setSemanticType(SemanticStepType type) {
    this.semanticType = type;
  }

  @Override
  public Set<String> variables() {
    return variables;
  }

  @Override
  @Nullable
  public DocStringTree docString() {
    return docString;
  }

  @Override
  @Nullable
  public TableTree table() {
    return table;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitStep(this);
  }

  private void initVariables() {
    variables = new HashSet<>();

    addVariablesFromText(sentence.text());

    if (table != null) {
      table.rows().stream().map(SyntaxToken::text).forEach(this::addVariablesFromText);
    }
  }

  private void addVariablesFromText(String text) {
    Matcher matcher = Pattern.compile("<(.+?)>").matcher(text);
    while (matcher.find()) {
      variables.add(matcher.group(1).trim());
    }
  }

  private void initData(@Nullable Tree data) {
    if (data != null) {
      if (data instanceof DocStringTree) {
        docString = (DocStringTree) data;
        table = null;
      } else if (data instanceof TableTree) {
        table = (TableTree) data;
        docString = null;
      } else {
        throw new IllegalStateException("Unsupported data tree: " + data.toString());
      }
    } else {
      docString = null;
      table = null;
    }
  }

}
