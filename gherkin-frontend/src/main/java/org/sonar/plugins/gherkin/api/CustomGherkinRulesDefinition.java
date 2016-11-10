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
package org.sonar.plugins.gherkin.api;

import com.google.common.collect.ImmutableList;
import org.sonar.api.ExtensionPoint;
import org.sonar.api.batch.BatchSide;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

/**
 * Extension point to create custom rule repository for Gherkin.
 */
@ExtensionPoint
@BatchSide
public abstract class CustomGherkinRulesDefinition implements RulesDefinition {

  /**
   * Defines rule repository with check metadata from check classes' annotations.
   * This method should be overridden if check metadata are provided via another format,
   * e.g: XMl, JSON.
   */
  @Override
  public void define(Context context) {
    NewRepository repo = context.createRepository(repositoryKey(), "gherkin").setName(repositoryName());
    new AnnotationBasedRulesDefinition(repo, "gherkin").addRuleClasses(false, ImmutableList.copyOf(checkClasses()));
    repo.done();
  }

  /**
   * Name of the custom rule repository.
   */
  public abstract String repositoryName();

  /**
   * Key of the custom rule repository.
   */
  public abstract String repositoryKey();

  /**
   * Array of the custom rules classes.
   */
  public abstract Class[] checkClasses();
}
