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

import java.io.File;

public class GenerateRuleDescriptionsBatch {

  private static final String TEMPLATE_DIRECTORY = "gherkin-checks/src/main/resources/org/sonar/l10n/gherkin/rules/gherkin/template/";
  private static final String TARGET_DIRECTORY = "gherkin-checks/target/classes/org/sonar/l10n/gherkin/rules/gherkin/";

  private static final RuleDescriptionsGenerator RULE_DESCRIPTIONS_GENERATOR = new RuleDescriptionsGenerator();

  private GenerateRuleDescriptionsBatch() {
  }

  public static void main(String... args) {
    generateRuleDescriptionsFromTemplates(TEMPLATE_DIRECTORY, TARGET_DIRECTORY);
  }

  private static void generateRuleDescriptionsFromTemplates(String templateDirectoryPath, String targetDirectoryPath) {
    File[] files = new File(templateDirectoryPath).listFiles();
    for (File file : files) {
      RULE_DESCRIPTIONS_GENERATOR.generateHtmlRuleDescription(file.getPath(), targetDirectoryPath + file.getName());
    }
  }

}
