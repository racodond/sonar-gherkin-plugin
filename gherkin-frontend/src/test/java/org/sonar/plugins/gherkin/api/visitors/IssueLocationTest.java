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
package org.sonar.plugins.gherkin.api.visitors;

import org.junit.Test;
import org.sonar.gherkin.tree.impl.InternalSyntaxToken;
import org.sonar.plugins.gherkin.api.tree.Tree;
import org.sonar.plugins.gherkin.api.visitors.issue.IssueLocation;

import java.io.File;
import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class IssueLocationTest {

  private static final File FILE = mock(File.class);
  private static final String MESSAGE = "message";

  @Test
  public void several_lines_tokens() throws Exception {
    String tokenValue = "blabla\\\nblabla...";

    IssueLocation location = new IssueLocation(FILE, createToken(3, 2, tokenValue), MESSAGE);
    assertThat(location.startLine()).isEqualTo(3);
    assertThat(location.endLine()).isEqualTo(4);
    assertThat(location.startLineOffset()).isEqualTo(2);
    assertThat(location.endLineOffset()).isEqualTo(9);
    assertThat(location.file()).isEqualTo(FILE);
  }

  private Tree createToken(int line, int column, String tokenValue) {
    return new InternalSyntaxToken(line, column, tokenValue, Collections.emptyList(), false, false);
  }

}
