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
package org.sonar.gherkin.checks;

import org.junit.Test;
import org.sonar.gherkin.checks.verifier.GherkinCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class AllowedTagsCheckTest {

  @Test
  public void test_default_regular_expression() {
    GherkinCheckVerifier.verify(new AllowedTagsCheck(), CheckTestUtils.getTestFile("allowed-tags/allowed-tags-default.feature"));
  }

  @Test
  public void test_custom_regular_expression() {
    AllowedTagsCheck check = new AllowedTagsCheck();
    check.setAllowedTags("mytag|yourtag");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("allowed-tags/allowed-tags-custom.feature"));
  }

  @Test
  public void test_composite_tags() {
    AllowedTagsCheck check = new AllowedTagsCheck();
    check.setAllowedTags("us:\\d+|uid:[a-zA-Z''-'\\s]{1,40}");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("allowed-tags/allowed-tags-composite.feature"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_is_not_valid() {
    try {
      AllowedTagsCheck check = new AllowedTagsCheck();
      check.setAllowedTags("(");

      GherkinCheckVerifier.issues(check, CheckTestUtils.getTestFile("allowed-tags/allowed-tags-custom.feature")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:allowed-tags (Only tags matching a regular expression should be used): "
        + "allowedTags parameter \"(\" is not a valid regular expression.");
    }
  }

}
