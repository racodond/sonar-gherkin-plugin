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
package org.sonar.plugins.gherkin;

import com.google.common.collect.ImmutableList;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.gherkin.checks.*;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

import java.util.Collection;

public class GherkinRulesDefinition implements RulesDefinition {

  public static final String REPOSITORY_KEY = "gherkin";
  private static final String REPOSITORY_NAME = "SonarQube";

  @Override
  public void define(Context context) {
    NewRepository repository = context
      .createRepository(REPOSITORY_KEY, GherkinLanguage.KEY)
      .setName(REPOSITORY_NAME);

    new AnnotationBasedRulesDefinition(repository, GherkinLanguage.KEY).addRuleClasses(false, getChecks());
    repository.done();
  }

  @SuppressWarnings("rawtypes")
  public static Collection<Class> getChecks() {
    return ImmutableList.of(
      AddCommonGivenStepsToBackgroundCheck.class,
      AllowedTagsCheck.class,
      AllStepTypesInScenarioCheck.class,
      BOMCheck.class,
      CommentConventionCheck.class,
      CommentRegularExpressionCheck.class,
      DuplicatedFeatureNamesCheck.class,
      DuplicatedScenarioNamesCheck.class,
      DuplicatedStepsCheck.class,
      EndLineCharactersCheck.class,
      FileNameCheck.class,
      FixmeTagPresenceCheck.class,
      GivenStepRegularExpressionCheck.class,
      IncompleteExamplesTableCheck.class,
      IndentationCheck.class,
      MaxNumberScenariosCheck.class,
      MaxNumberStepsCheck.class,
      MissingDataTableColumnCheck.class,
      MissingFeatureDescriptionCheck.class,
      MissingFeatureNameCheck.class,
      MissingNewlineAtEndOfFileCheck.class,
      MissingScenarioNameCheck.class,
      NoFeatureCheck.class,
      NoScenarioCheck.class,
      NoStepCheck.class,
      NoTagExamplesCheck.class,
      OnlyGivenStepsInBackgroundCheck.class,
      ParsingErrorCheck.class,
      StarStepPrefixCheck.class,
      StepOfUnknownTypeCheck.class,
      StepSentenceLengthCheck.class,
      StepsRightOrderCheck.class,
      TabCharacterCheck.class,
      TagNameCheck.class,
      TagRightLevelCheck.class,
      ThenStepRegularExpressionCheck.class,
      TodoTagPresenceCheck.class,
      TrailingWhitespaceCheck.class,
      UnusedVariableCheck.class,
      UseAndButCheck.class,
      UselessTagCheck.class,
      WhenStepRegularExpressionCheck.class,
      WordingBusinessLevelCheck.class
    );
  }

}
