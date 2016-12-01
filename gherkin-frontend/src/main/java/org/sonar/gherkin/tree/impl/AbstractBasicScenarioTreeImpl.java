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

    setStepTypes();
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

  private void setStepTypes() {
    for (int i = 0; i < steps.size(); i++) {
      StepTree currentStep = steps.get(i);
      switch (currentStep.prefix().text()) {
        case "Given":
          currentStep.setType(StepTree.StepType.GIVEN);
          break;

        case "When":
          currentStep.setType(StepTree.StepType.WHEN);
          break;

        case "Then":
          currentStep.setType(StepTree.StepType.THEN);
          break;

        case "And":
        case "But":
          if (i > 0) {
            currentStep.setType(steps.get(i - 1).type());
          } else {
            currentStep.setType(StepTree.StepType.UNKNOWN);
          }
          break;

        default:
          currentStep.setType(StepTree.StepType.UNKNOWN);
      }
    }
  }

}
