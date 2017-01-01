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
package org.sonar.plugins.gherkin.api.tree;

import javax.annotation.Nullable;
import java.util.Set;

public interface StepTree extends Tree {

  enum SemanticStepType {
    GIVEN("Given"),
    WHEN("When"),
    THEN("Then"),
    UNKNOWN("Unknown");

    private String value;

    SemanticStepType(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  enum SyntacticStepType {
    GIVEN("Given"),
    WHEN("When"),
    THEN("Then"),
    STAR("*"),
    AND("And"),
    BUT("But");

    private String value;

    SyntacticStepType(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  PrefixTree prefix();

  StepSentenceTree sentence();

  SemanticStepType semanticType();

  void setSemanticType(SemanticStepType type);

  SyntacticStepType syntacticType();

  void setSyntacticType(SyntacticStepType type);

  Set<String> variables();

  @Nullable
  DocStringTree docString();

  @Nullable
  TableTree table();

}
