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

import static org.fest.assertions.Assertions.assertThat;

public class TagNameCheckTest {

  private TagNameCheck check = new TagNameCheck();

  @Test
  public void all_tags_should_follow_the_default_naming_convention_and_not_raise_any_issue() {
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("tag-name/tag-name-ok.feature"));
  }

  @Test
  public void some_tags_should_not_follow_the_default_naming_convention_and_raise_some_issues() {
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("tag-name/tag-name-ko.feature"));
  }

  @Test
  public void all_tags_should_follow_a_custom_naming_convention_and_not_raise_any_issue() {
    check.setFormat("^[a-z][-a-z]*$");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("tag-name/tag-name-custom-ok.feature"));
  }

  @Test
  public void some_tags_should_not_follow_a_custom_naming_convention_and_raise_some_issues() {
    check.setFormat("^[a-z]+$");
    GherkinCheckVerifier.verify(check, CheckTestUtils.getTestFile("tag-name/tag-name-custom-ko.feature"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_format_parameter_regular_expression_is_not_valid() {
    try {
      check.setFormat("(");
      GherkinCheckVerifier.issues(check, CheckTestUtils.getTestFile("tag-name/tag-name-ok.feature")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:tag-naming-convention (Tags should comply with a naming convention): " +
        "format parameter \"(\" is not a valid regular expression.");
    }
  }

}
