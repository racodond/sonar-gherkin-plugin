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
package org.sonar.gherkin.parser;

import org.junit.Test;

public class ScenarioPrefixTreeTest extends PrefixTreeTest {

  public ScenarioPrefixTreeTest() {
    super(GherkinLexicalGrammar.SCENARIO_PREFIX);
  }

  @Test
  public void scenarioPrefix() throws Exception {
    checkParsed("Scenario", "Scenario");
    checkParsed(" Scenario", "Scenario");
  }

  @Test
  public void notScenarioPrefix() throws Exception {
    checkNotParsed("scenario");
    checkNotParsed("SCENARIO");
  }

}
