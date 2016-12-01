SonarQube Cucumber Gherkin Plugin
=================================

[![Build Status](https://api.travis-ci.org/racodond/sonar-gherkin-plugin.svg?branch=master)](https://travis-ci.org/racodond/sonar-gherkin-plugin)
[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/hhh9gsp77hatvai1/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-gherkin-plugin/branch/master)
[![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.racodond.sonarqube.plugin.gherkin:gherkin)](https://sonarqube.com/overview?id=com.racodond.sonarqube.plugin.gherkin%3Agherkin)

## Description
This plugin enables code QA analysis of [Cucumber Gherkin feature files](https://github.com/cucumber/cucumber/wiki/Gherkin) within [SonarQube](http://www.sonarqube.org):

 * Computes metrics: lines of code, comments lines, etc.
 * Performs more than [15 checks](#available-checks)
 * Provide the ability to write your own checks

## Usage

1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
1. Install the Cucumber Gherkin plugin either by a [direct download](https://github.com/racodond/sonar-gherkin-plugin/releases) or through the [Update Center](http://docs.sonarqube.org/display/SONAR/Update+Center).
1. [Install your favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.) and analyze your code. Note that Java 8 is required to run an analysis.

Plugin versions and compatibility with SonarQube versions: [http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix](http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix)

## Metrics

### Statements
Number of steps.

### Functions
Number of scenarios and scenario outlines.

### Classes
Number of features.


## Available Checks

### Standard

 * "Examples" table should contain data
 * "FIXME" tags should be handled
 * "TODO" tags should be handled
 * Add common Given steps to Background
 * Background should only contain Given steps
 * All comments should be formatted consistently
 * Byte Order Mark (BOM) should not be used for UTF-8 files
 * Each feature should have a name
 * Each feature should have a unique name
 * Each scenario should have a name
 * Each scenario should have a unique name
 * End-line characters should be consistent
 * Features should not contain too many scenarios
 * Features that do not define any scenario should be removed
 * File names should comply with a naming convention
 * Files should contain an empty new line at the end
 * Files that do not define any feature should be removed
 * Given steps should follow a regular expression
 * Lines should not end with trailing whitespaces
 * Only tags from the whitelist should be used
 * Scenarios should not contain too many steps
 * Scenarios that do not define any step should be removed
 * Source code should be properly indented
 * Star (*) step prefix should not be used
 * Tabulation characters should not be used
 * Tags should be defined at the right level
 * Tags should comply with a naming convention
 * Tags should not be set on Examples
 * Then steps should follow a regular expression
 * Useless tags should be removed
 * When steps should follow a regular expression
 
### Templates

 * Regular expression on comment


## Custom Checks

You're thinking of new valuable rules? Version 1.0 or greater provides an API to write your own custom checks.
A sample plugin with detailed explanations is available [here](https://github.com/racodond/sonar-gherkin-custom-rules-plugin).
If your custom rules may benefit the community, feel free to create a pull request in order to make the rule available in the Cucumber Gherkin plugin.

You're thinking of new rules that may benefit the community but don't have the time or the skills to write them? Feel free to create an [issue](https://github.com/racodond/sonar-gherkin-plugin/issues) for your rules to be taken under consideration.
