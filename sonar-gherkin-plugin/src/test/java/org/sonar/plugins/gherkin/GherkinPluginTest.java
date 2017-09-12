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
package org.sonar.plugins.gherkin;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.utils.Version;

import static org.fest.assertions.Assertions.assertThat;

public class GherkinPluginTest {

  @Test
  public void should_get_the_right_version() {
    Plugin.Context context = new Plugin.Context(Version.create(5, 6));
    new GherkinPlugin().define(context);
    assertThat(context.getSonarQubeVersion().major()).isEqualTo(5);
    assertThat(context.getSonarQubeVersion().minor()).isEqualTo(6);
  }

  @Test
  public void should_get_the_right_number_of_extensions() {
    Plugin.Context context = new Plugin.Context(Version.create(5, 6));
    new GherkinPlugin().define(context);
    assertThat(context.getExtensions()).hasSize(4);
  }

}
