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

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class GherkinDialectProvider {

  public static final Pattern LANGUAGE_DECLARATION_PATTERN = Pattern.compile("#\\s*language:\\s+([^\\s]+)\\s*");
  public static final String DEFAULT_LANGUAGE = "en";

  private static final Map<String, Map<String, List<String>>> DIALECTS;

  static {
    Gson gson = new Gson();
    try {
      Reader dialects = new InputStreamReader(GherkinDialectProvider.class.getResourceAsStream("/org/sonar/gherkin/parser/gherkin-languages.json"), "UTF-8");
      DIALECTS = gson.fromJson(dialects, Map.class);
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("Error while reading gherkin-languages.json file", e);
    }
  }

  private GherkinDialectProvider() {
  }

  public static GherkinDialect getDialect(String language) {
    Map<String, List<String>> map = DIALECTS.get(language);
    if (map == null) {
      throw new IllegalStateException("Unsupported language: " + language);
    }
    return new GherkinDialect(language, map);
  }

}
