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

import com.sonar.sslr.api.typed.Optional;
import org.sonar.gherkin.tree.impl.*;
import org.sonar.plugins.gherkin.api.tree.*;

import java.util.List;

public class TreeFactory {

  public GherkinDocumentTree gherkinDocument(Optional<SyntaxToken> byteOrderMark, Optional<FeatureTree> feature, SyntaxToken eof) {
    return new GherkinDocumentTreeImpl(byteOrderMark.orNull(), feature.orNull(), eof);
  }

  public FeatureTree feature(FeatureDeclarationTree featureDeclaration, Optional<BackgroundTree> background, Optional<List<BasicScenarioTree>> allScenarios) {
    return new FeatureTreeImpl(featureDeclaration, background.orNull(), allScenarios.orNull());
  }

  public FeatureDeclarationTree featureDeclaration(Optional<List<TagTree>> tags, PrefixTree prefix, SyntaxToken colon, NameTree name, Optional<DescriptionTree> description) {
    return new FeatureDeclarationTreeImpl(tags.orNull(), prefix, colon, name, description.orNull());
  }

  public BackgroundTree background(PrefixTree prefix, SyntaxToken colon, Optional<DescriptionTree> description, Optional<List<StepTree>> steps) {
    return new BackgroundTreeImpl(prefix, colon, description.orNull(), steps.orNull());
  }

  public ScenarioTree scenario(Optional<List<TagTree>> tags, PrefixTree prefix, SyntaxToken colon, NameTree name, Optional<DescriptionTree> description, Optional<List<StepTree>> steps) {
    return new ScenarioTreeImpl(tags.orNull(), prefix, colon, name, description.orNull(), steps.orNull());
  }

  public ScenarioOutlineTree scenarioOutline(Optional<List<TagTree>> tags, PrefixTree prefix, SyntaxToken colon, NameTree name, Optional<DescriptionTree> description, Optional<List<StepTree>> steps, ExamplesTree examples) {
    return new ScenarioOutlineTreeImpl(tags.orNull(), prefix, colon, name, description.orNull(), steps.orNull(), examples);
  }

  public ExamplesTree examples(Optional<List<TagTree>> tags, PrefixTree prefix, SyntaxToken colon, Optional<DescriptionTree> description, Optional<TableTree> table) {
    return new ExamplesTreeImpl(tags.orNull(), prefix, colon, description.orNull(), table.orNull());
  }

  public StepTree step(PrefixTree prefix, SyntaxToken sentence, Optional<Tree> data) {
    return new StepTreeImpl(prefix, sentence, data.orNull());
  }

  public TagTree tag(SyntaxToken prefix, SyntaxToken value) {
    return new TagTreeImpl(prefix, value);
  }

  public DescriptionTree featureDescription(List<SyntaxToken> descriptionLines) {
    return new DescriptionTreeImpl(descriptionLines);
  }

  public DescriptionTree scenarioDescription(List<SyntaxToken> descriptionLines) {
    return new DescriptionTreeImpl(descriptionLines);
  }

  public DescriptionTree examplesDescription(List<SyntaxToken> descriptionLines) {
    return new DescriptionTreeImpl(descriptionLines);
  }

  public FeaturePrefixTree featurePrefix(SyntaxToken keyword) {
    return new FeaturePrefixTreeImpl(keyword);
  }

  public BackgroundPrefixTree backgroundPrefix(SyntaxToken keyword) {
    return new BackgroundPrefixTreeImpl(keyword);
  }

  public ScenarioPrefixTree scenarioPrefix(SyntaxToken keyword) {
    return new ScenarioPrefixTreeImpl(keyword);
  }

  public ScenarioOutlinePrefixTree scenarioOutlinePrefix(SyntaxToken keyword) {
    return new ScenarioOutlinePrefixTreeImpl(keyword);
  }

  public ExamplesPrefixTree examplesPrefix(SyntaxToken keyword) {
    return new ExamplesPrefixTreeImpl(keyword);
  }

  public StepPrefixTree stepPrefix(SyntaxToken keyword) {
    return new StepPrefixTreeImpl(keyword);
  }

  public NameTree name(SyntaxToken name) {
    return new NameTreeImpl(name);
  }

  public DocStringTree docString(SyntaxToken prefix, Optional<SyntaxToken> contentType, Optional<List<SyntaxToken>> data, SyntaxToken suffix) {
    return new DocStringTreeImpl(prefix, contentType.orNull(), data.orNull(), suffix);
  }

  public TableTree table(List<SyntaxToken> rows) {
    return new TableTreeImpl(rows);
  }

}
