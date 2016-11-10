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
import org.sonar.plugins.gherkin.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeatureTreeImpl extends GherkinTree implements FeatureTree {

  private final FeatureDeclarationTree declaration;
  private final BackgroundTree background;
  private final List<BasicScenarioTree> allScenarios;
  private final List<ScenarioTree> scenarios;
  private final List<ScenarioOutlineTree> scenarioOutlines;

  public FeatureTreeImpl(FeatureDeclarationTree declaration, @Nullable BackgroundTree background, @Nullable List<BasicScenarioTree> allScenarios) {
    this.declaration = declaration;
    this.background = background;

    if (allScenarios != null) {
      this.allScenarios = allScenarios;
    } else {
      this.allScenarios = new ArrayList<>();
    }

    this.scenarios = TreeListUtils.allElementsOfType(allScenarios, ScenarioTree.class);
    this.scenarioOutlines = TreeListUtils.allElementsOfType(allScenarios, ScenarioOutlineTree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.FEATURE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(declaration, background),
      allScenarios.iterator());
  }

  @Override
  public FeatureDeclarationTree declaration() {
    return declaration;
  }

  @Override
  @Nullable
  public BackgroundTree background() {
    return background;
  }

  @Override
  public List<ScenarioTree> scenarios() {
    return scenarios;
  }

  @Override
  public List<ScenarioOutlineTree> scenarioOutlines() {
    return scenarioOutlines;
  }

  @Override
  public List<BasicScenarioTree> allScenarios() {
    return allScenarios;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitFeature(this);
  }

}
