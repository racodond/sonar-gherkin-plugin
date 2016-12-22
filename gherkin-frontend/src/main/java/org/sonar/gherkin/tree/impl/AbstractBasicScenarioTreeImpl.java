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
import org.sonar.gherkin.parser.GherkinDialect;
import org.sonar.gherkin.parser.GherkinDialectProvider;
import org.sonar.plugins.gherkin.api.tree.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractBasicScenarioTreeImpl extends GherkinTree implements BasicScenarioTree {

  private final PrefixTree prefix;
  private final SyntaxToken colon;
  private final NameTree name;
  private final DescriptionTree description;
  private final List<StepTree> steps;

  public AbstractBasicScenarioTreeImpl(PrefixTree prefix, SyntaxToken colon, @Nullable NameTree name, @Nullable DescriptionTree description, @Nullable List<StepTree> steps) {
    this.prefix = prefix;
    this.colon = colon;
    this.name = name;
    this.description = description;
    this.steps = steps != null ? steps : new ArrayList<>();
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(prefix, colon, name, description),
      steps.iterator());
  }

  @Override
  public PrefixTree prefix() {
    return prefix;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  @Nullable
  public NameTree name() {
    return name;
  }

  @Override
  @Nullable
  public DescriptionTree description() {
    return description;
  }

  @Override
  public List<StepTree> steps() {
    return steps;
  }

  @Override
  public void setStepTypes(String language) {
    setSyntacticStepTypes(language);
    setSemanticStepTypes();
  }

  private void setSemanticStepTypes() {
    for (int i = 0; i < steps.size(); i++) {
      StepTree currentStep = steps.get(i);
      switch (currentStep.syntacticType()) {
        case GIVEN:
          currentStep.setSemanticType(StepTree.SemanticStepType.GIVEN);
          break;

        case WHEN:
          currentStep.setSemanticType(StepTree.SemanticStepType.WHEN);
          break;

        case THEN:
          currentStep.setSemanticType(StepTree.SemanticStepType.THEN);
          break;

        case AND:
        case BUT:
          if (i > 0) {
            currentStep.setSemanticType(steps.get(i - 1).semanticType());
          } else {
            currentStep.setSemanticType(StepTree.SemanticStepType.UNKNOWN);
          }
          break;

        default:
          currentStep.setSemanticType(StepTree.SemanticStepType.UNKNOWN);
      }
    }
  }

  private void setSyntacticStepTypes(String language) {
    GherkinDialect dialect = GherkinDialectProvider.getDialect(language);
    String stepPrefix;
    for (StepTree currentStep : steps) {
      stepPrefix = currentStep.prefix().text();
      if (dialect.getGivenStepKeywords().contains(stepPrefix)) {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.GIVEN);
      } else if (dialect.getWhenStepKeywords().contains(stepPrefix)) {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.WHEN);
      } else if (dialect.getThenStepKeywords().contains(stepPrefix)) {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.THEN);
      } else if (dialect.getButStepKeywords().contains(stepPrefix)) {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.BUT);
      } else if (dialect.getAndStepKeywords().contains(stepPrefix)) {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.AND);
      } else {
        currentStep.setSyntacticType(StepTree.SyntacticStepType.STAR);
      }
    }
  }

}
