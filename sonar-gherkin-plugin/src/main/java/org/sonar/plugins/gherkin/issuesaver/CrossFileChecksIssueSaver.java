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
package org.sonar.plugins.gherkin.issuesaver;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.gherkin.issuesaver.crossfile.CrossFileCheckIssueSaver;
import org.sonar.plugins.gherkin.issuesaver.crossfile.DuplicatedScenarioNamesIssueSaver;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class CrossFileChecksIssueSaver {

  private CrossFileChecksIssueSaver() {
  }

  private static Collection<Class<? extends CrossFileCheckIssueSaver>> getCrossFileCheckIssueSavers() {
    return ImmutableList.of(
      DuplicatedScenarioNamesIssueSaver.class
    );
  }

  public static void saveIssues(IssueSaver issueSaver) {
    getCrossFileCheckIssueSavers().forEach(c -> saveIssuesOnCheck(c, issueSaver));
  }

  private static void saveIssuesOnCheck(Class<? extends CrossFileCheckIssueSaver> clazz, IssueSaver issueSaver) {
    try {
      clazz.getConstructor(IssueSaver.class).newInstance(issueSaver).saveIssues();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException("Cannot save issues on check " + clazz.getName(), e);
    }
  }

}
