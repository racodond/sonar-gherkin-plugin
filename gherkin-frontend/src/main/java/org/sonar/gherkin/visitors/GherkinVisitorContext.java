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
package org.sonar.gherkin.visitors;

import org.sonar.plugins.gherkin.api.tree.GherkinDocumentTree;
import org.sonar.plugins.gherkin.api.visitors.TreeVisitorContext;

import java.io.File;

public class GherkinVisitorContext implements TreeVisitorContext {

  private final GherkinDocumentTree tree;
  private final File file;

  public GherkinVisitorContext(GherkinDocumentTree tree, File file) {
    this.tree = tree;
    this.file = file;
  }

  @Override
  public GherkinDocumentTree getTopTree() {
    return tree;
  }

  @Override
  public File getFile() {
    return file;
  }

}
