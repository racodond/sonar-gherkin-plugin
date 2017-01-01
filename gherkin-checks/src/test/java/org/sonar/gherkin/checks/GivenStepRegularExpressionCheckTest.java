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

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class GivenStepRegularExpressionCheckTest {

  private final static File FILE = CheckTestUtils.getTestFile("given-step-regular-expression.feature");
  private GivenStepRegularExpressionCheck check = new GivenStepRegularExpressionCheck();

  @Test
  public void should_not_match_some_sentences_and_raise_some_issues() {
    check.regularExpression = "^I .*$";
    GherkinCheckVerifier.verify(check, FILE);
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_is_not_valid() {
    try {
      check.regularExpression = "(";

      GherkinCheckVerifier.issues(check, FILE).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:given-step-regular-expression (Given steps should follow a regular expression): "
        + "regularExpression parameter \"(\" is not a valid regular expression.");
    }
  }

}
