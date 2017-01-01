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
package org.sonar.gherkin.checks;

import org.junit.Test;
import org.sonar.gherkin.checks.verifier.GherkinCheckVerifier;

public class MaxNumberScenariosCheckTest {

  @Test
  public void should_not_raise_any_issue_because_the_number_of_scenarios_is_lower_than_the_default_threshold() {
    GherkinCheckVerifier.verify(new MaxNumberScenariosCheck(), CheckTestUtils.getTestFile("max-number-scenarios/lower-than-default-threshold.feature"));
  }

  @Test
  public void should_raise_an_issue_because_the_number_of_scenarios_is_greater_than_the_default_threshold() {
    GherkinCheckVerifier.verify(new MaxNumberScenariosCheck(), CheckTestUtils.getTestFile("max-number-scenarios/greater-than-default-threshold.feature"));
  }

  @Test
  public void should_not_raise_any_issue_because_the_number_of_scenarios_is_lower_than_the_custom_threshold() {
    MaxNumberScenariosCheck check = new MaxNumberScenariosCheck();
    check.setThreshold(4);
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("max-number-scenarios/lower-than-custom-threshold.feature"));
  }

  @Test
  public void should_raise_an_issue_because_the_number_of_scenarios_is_greater_than_the_custom_threshold() {
    MaxNumberScenariosCheck check = new MaxNumberScenariosCheck();
    check.setThreshold(4);
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("max-number-scenarios/greater-than-custom-threshold.feature"));
  }

}
