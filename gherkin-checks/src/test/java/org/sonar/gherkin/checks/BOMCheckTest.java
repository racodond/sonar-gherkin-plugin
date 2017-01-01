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

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.gherkin.checks.verifier.GherkinCheckVerifier;

public class BOMCheckTest {

  @Test
  public void should_find_that_the_UTF8_file_starts_with_a_BOM_and_raise_an_issue() {
    GherkinCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("bom/utf8-with-bom.feature"),
      Charsets.UTF_8);
  }

  @Test
  public void should_find_that_the_UTF8_file_does_not_start_with_a_BOM_and_not_raise_any_issue() {
    GherkinCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("bom/utf8.feature"),
      Charsets.UTF_8);
  }

  @Test
  public void should_find_that_the_UTF16_files_start_with_a_BOM_but_not_raise_any_issue() {
    GherkinCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("bom/utf16-be.feature"),
      Charsets.UTF_16BE);

    GherkinCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("bom/utf16-le.feature"),
      Charsets.UTF_16LE);
  }

}
