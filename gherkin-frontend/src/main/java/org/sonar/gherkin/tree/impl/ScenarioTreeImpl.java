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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScenarioTreeImpl extends AbstractBasicScenarioTreeImpl implements ScenarioTree {

  private final List<TagTree> tags;

  public ScenarioTreeImpl(@Nullable List<TagTree> tags, PrefixTree prefix, SyntaxToken colon, @Nullable NameTree name, @Nullable DescriptionTree description, @Nullable List<StepTree> steps) {
    super(prefix, colon, name, description, steps);
    this.tags = tags != null ? tags : new ArrayList<>();
  }

  @Override
  public Kind getKind() {
    return Kind.SCENARIO;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      tags.iterator(),
      super.childrenIterator());
  }

  @Override
  public List<TagTree> tags() {
    return tags;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScenario(this);
  }

}
