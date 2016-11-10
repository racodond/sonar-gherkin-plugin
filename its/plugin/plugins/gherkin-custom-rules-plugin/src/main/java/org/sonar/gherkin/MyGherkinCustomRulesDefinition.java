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
package org.sonar.gherkin;

import org.sonar.gherkin.checks.ForbiddenTagCheck;
import org.sonar.gherkin.checks.ForbiddenNameContentCheck;
import org.sonar.plugins.gherkin.api.CustomGherkinRulesDefinition;

/**
 * Extension point to define a Gherkin rule repository.
 */
public class MyGherkinCustomRulesDefinition extends CustomGherkinRulesDefinition {

  /**
   * Provide the repository name.
   */
  @Override
  public String repositoryName() {
    return "My Gherkin Custom Repository";
  }

  /**
   * Provide the repository key.
   */
  @Override
  public String repositoryKey() {
    return "custom-gherkin";
  }

  /**
   * Provide the list of classes implementing rules.
   */
  @Override
  public Class[] checkClasses() {
    return new Class[]{
      ForbiddenTagCheck.class,
      ForbiddenNameContentCheck.class
    };
  }
}
