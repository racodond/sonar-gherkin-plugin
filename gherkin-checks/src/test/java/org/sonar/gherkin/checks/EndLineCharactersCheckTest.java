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
import com.google.common.io.Files;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.gherkin.checks.verifier.GherkinCheckVerifier;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class EndLineCharactersCheckTest {

  private EndLineCharactersCheck check = new EndLineCharactersCheck();

  @Test
  public void should_find_only_crlf_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r\n"))
      .noMore();
  }

  @Test
  public void should_find_only_cr_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("CR");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r"))
      .noMore();
  }

  @Test
  public void should_find_only_lf_and_not_raise_any_issues() throws Exception {
    check.setEndLineCharacters("LF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\n"))
      .noMore();
  }

  @Test
  public void crlf_should_find_lf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\n"))
      .next().withMessage("Set all end-line characters to 'CRLF' in this file.")
      .noMore();
  }

  @Test
  public void crlf_should_find_cr_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CRLF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r"))
      .next().withMessage("Set all end-line characters to 'CRLF' in this file.")
      .noMore();
  }

  @Test
  public void cr_should_find_crlf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CR");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r\n"))
      .next().withMessage("Set all end-line characters to 'CR' in this file.")
      .noMore();
  }

  @Test
  public void cr_should_find_lf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("CR");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\n"))
      .next().withMessage("Set all end-line characters to 'CR' in this file.")
      .noMore();
  }

  @Test
  public void lf_should_find_crlf_and_raise_issues() throws Exception {
    check.setEndLineCharacters("LF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r\n"))
      .next().withMessage("Set all end-line characters to 'LF' in this file.")
      .noMore();
  }

  @Test
  public void lf_should_find_cr_and_raise_issues() throws Exception {
    check.setEndLineCharacters("LF");
    GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r"))
      .next().withMessage("Set all end-line characters to 'LF' in this file.")
      .noMore();
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_endLineCharacters_parameter_is_not_valid() throws Exception {
    try {
      check.setEndLineCharacters("abc");
      GherkinCheckVerifier.issues(check, getTestFileWithProperEndLineCharacters("\r")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check gherkin:end-line-characters (End-line characters should be consistent): "
        + "endLineCharacters parameter is not valid.\nActual: 'abc'\nExpected: 'CR' or 'CRLF' or 'LF'");
    }
  }

  private static File getTestFileWithProperEndLineCharacters(String endLineCharacter) throws Exception {
    TemporaryFolder temporaryFolder = new TemporaryFolder();
    File testFile = temporaryFolder.newFile();
    Files.write(
      Files.toString(CheckTestUtils.getTestFile("end-line-characters.feature"), Charsets.UTF_8)
        .replaceAll("\\r\\n", "\n")
        .replaceAll("\\r", "\n")
        .replaceAll("\\n", endLineCharacter),
      testFile, Charsets.UTF_8);
    return testFile;
  }

}
