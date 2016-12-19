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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GherkinDialect {

  private final Map<String, List<String>> keywords;
  private String language;

  public GherkinDialect(String language, Map<String, List<String>> keywords) {
    this.language = language;
    this.keywords = keywords;
  }

  public Set<String> getFeatureKeywords() {
    return keywords.get("feature").stream().collect(Collectors.toSet());
  }

  public Set<String> getScenarioKeywords() {
    return keywords.get("scenario").stream().collect(Collectors.toSet());
  }

  public Set<String> getStepKeywords() {
    Set<String> result = new HashSet<>();
    result.addAll(keywords.get("given"));
    result.addAll(keywords.get("when"));
    result.addAll(keywords.get("then"));
    result.addAll(keywords.get("and"));
    result.addAll(keywords.get("but"));
    return result;
  }

  public Set<String> getGivenStepKeywords() {
    return keywords.get("given").stream().filter(k -> !"* ".equals(k)).collect(Collectors.toSet());
  }

  public Set<String> getWhenStepKeywords() {
    return keywords.get("when").stream().filter(k -> !"* ".equals(k)).collect(Collectors.toSet());
  }

  public Set<String> getThenStepKeywords() {
    return keywords.get("then").stream().filter(k -> !"* ".equals(k)).collect(Collectors.toSet());
  }

  public Set<String> getAndStepKeywords() {
    return keywords.get("and").stream().filter(k -> !"* ".equals(k)).collect(Collectors.toSet());
  }

  public Set<String> getButStepKeywords() {
    return keywords.get("but").stream().filter(k -> !"* ".equals(k)).collect(Collectors.toSet());
  }

  public Set<String> getBackgroundKeywords() {
    return keywords.get("background").stream().collect(Collectors.toSet());
  }

  public Set<String> getScenarioOutlineKeywords() {
    return keywords.get("scenarioOutline").stream().collect(Collectors.toSet());
  }

  public Set<String> getExamplesKeywords() {
    return keywords.get("examples").stream().collect(Collectors.toSet());
  }

  public String getLanguage() {
    return language;
  }
}
