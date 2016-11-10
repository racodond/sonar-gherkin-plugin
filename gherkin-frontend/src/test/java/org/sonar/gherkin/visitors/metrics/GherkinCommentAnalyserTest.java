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
package org.sonar.gherkin.visitors.metrics;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GherkinCommentAnalyserTest {

  private GherkinCommentAnalyser analyser = new GherkinCommentAnalyser();

  @Test
  public void content() {
    assertThat(analyser.getContents("# comment")).isEqualTo(" comment");
    assertThat(analyser.getContents("#comment")).isEqualTo("comment");
  }

  @Test
  public void blank() {
    assertThat(analyser.isBlank(" ")).isTrue();
    assertThat(analyser.isBlank("  ")).isTrue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void unknown_type_of_comments() {
    analyser.getContents("");
  }

}
