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
package org.sonar.gherkin.parser;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GherkinDialectProviderTest {

  @Test
  public void should_return_en_keywords() {
    GherkinDialect dialect = GherkinDialectProvider.getDialect("en");

    assertThat(dialect.getLanguage()).isEqualTo("en");

    assertThat(dialect.getFeatureKeywords()).hasSize(3);
    assertThat(dialect.getFeatureKeywords().contains("Feature")).isTrue();
    assertThat(dialect.getFeatureKeywords().contains("Ability")).isTrue();
    assertThat(dialect.getFeatureKeywords().contains("Business Need")).isTrue();

    assertThat(dialect.getBackgroundKeywords()).hasSize(1);
    assertThat(dialect.getBackgroundKeywords().contains("Background")).isTrue();

    assertThat(dialect.getScenarioKeywords()).hasSize(1);
    assertThat(dialect.getScenarioKeywords().contains("Scenario")).isTrue();

    assertThat(dialect.getScenarioOutlineKeywords()).hasSize(2);
    assertThat(dialect.getScenarioOutlineKeywords().contains("Scenario Outline")).isTrue();
    assertThat(dialect.getScenarioOutlineKeywords().contains("Scenario Template")).isTrue();

    assertThat(dialect.getExamplesKeywords()).hasSize(2);
    assertThat(dialect.getExamplesKeywords().contains("Examples")).isTrue();
    assertThat(dialect.getExamplesKeywords().contains("Scenarios")).isTrue();

    assertThat(dialect.getStepKeywords()).hasSize(6);
    assertThat(dialect.getStepKeywords().contains("Given ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("When ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Then ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("But ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("And ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("* ")).isTrue();

    assertThat(dialect.getGivenStepKeywords()).hasSize(1);
    assertThat(dialect.getGivenStepKeywords().contains("Given ")).isTrue();

    assertThat(dialect.getWhenStepKeywords()).hasSize(1);
    assertThat(dialect.getWhenStepKeywords().contains("When ")).isTrue();

    assertThat(dialect.getThenStepKeywords()).hasSize(1);
    assertThat(dialect.getThenStepKeywords().contains("Then ")).isTrue();

    assertThat(dialect.getAndStepKeywords()).hasSize(1);
    assertThat(dialect.getAndStepKeywords().contains("And ")).isTrue();

    assertThat(dialect.getButStepKeywords()).hasSize(1);
    assertThat(dialect.getButStepKeywords().contains("But ")).isTrue();
  }

  @Test
  public void should_return_fr_keywords() {
    GherkinDialect dialect = GherkinDialectProvider.getDialect("fr");

    assertThat(dialect.getLanguage()).isEqualTo("fr");

    assertThat(dialect.getFeatureKeywords()).hasSize(1);
    assertThat(dialect.getFeatureKeywords().contains("Fonctionnalité")).isTrue();

    assertThat(dialect.getBackgroundKeywords()).hasSize(1);
    assertThat(dialect.getBackgroundKeywords().contains("Contexte")).isTrue();

    assertThat(dialect.getScenarioKeywords()).hasSize(1);
    assertThat(dialect.getScenarioKeywords().contains("Scénario")).isTrue();

    assertThat(dialect.getScenarioOutlineKeywords()).hasSize(2);
    assertThat(dialect.getScenarioOutlineKeywords().contains("Plan du scénario")).isTrue();
    assertThat(dialect.getScenarioOutlineKeywords().contains("Plan du Scénario")).isTrue();

    assertThat(dialect.getExamplesKeywords()).hasSize(1);
    assertThat(dialect.getExamplesKeywords().contains("Exemples")).isTrue();

    assertThat(dialect.getStepKeywords()).hasSize(24);
    assertThat(dialect.getStepKeywords().contains("* ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Soit ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant donné que ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant donné qu'")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant donné ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant donnés ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant donnée ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Etant données ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant donné que ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant donné qu'")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant donné ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant donnée ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant donnés ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Étant données ")).isTrue();

    assertThat(dialect.getStepKeywords().contains("Quand ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Lorsque ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Lorsqu'")).isTrue();

    assertThat(dialect.getStepKeywords().contains("Alors ")).isTrue();

    assertThat(dialect.getStepKeywords().contains("Mais ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Mais que ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Mais qu'")).isTrue();

    assertThat(dialect.getStepKeywords().contains("Et ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Et que ")).isTrue();
    assertThat(dialect.getStepKeywords().contains("Et qu'")).isTrue();

    assertThat(dialect.getGivenStepKeywords()).hasSize(13);
    assertThat(dialect.getGivenStepKeywords().contains("Soit ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant donné que ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant donné qu'")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant donné ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant donnés ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant donnée ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Etant données ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant donné que ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant donné qu'")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant donné ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant donnée ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant donnés ")).isTrue();
    assertThat(dialect.getGivenStepKeywords().contains("Étant données ")).isTrue();

    assertThat(dialect.getWhenStepKeywords()).hasSize(3);
    assertThat(dialect.getWhenStepKeywords().contains("Quand ")).isTrue();
    assertThat(dialect.getWhenStepKeywords().contains("Lorsque ")).isTrue();
    assertThat(dialect.getWhenStepKeywords().contains("Lorsqu'")).isTrue();

    assertThat(dialect.getThenStepKeywords()).hasSize(1);
    assertThat(dialect.getThenStepKeywords().contains("Alors ")).isTrue();

    assertThat(dialect.getButStepKeywords()).hasSize(3);
    assertThat(dialect.getButStepKeywords().contains("Mais ")).isTrue();
    assertThat(dialect.getButStepKeywords().contains("Mais que ")).isTrue();
    assertThat(dialect.getButStepKeywords().contains("Mais qu'")).isTrue();

    assertThat(dialect.getAndStepKeywords()).hasSize(3);
    assertThat(dialect.getAndStepKeywords().contains("Et ")).isTrue();
    assertThat(dialect.getAndStepKeywords().contains("Et que ")).isTrue();
    assertThat(dialect.getAndStepKeywords().contains("Et qu'")).isTrue();
  }

  @Test(expected = IllegalStateException.class)
  public void should_throw_an_exception_because_language_is_not_supported() {
    GherkinDialectProvider.getDialect("blabla");
  }

}
