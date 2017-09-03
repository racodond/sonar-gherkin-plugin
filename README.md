SonarQube Cucumber Gherkin Analyzer
===================================

[![Build Status](https://api.travis-ci.org/racodond/sonar-gherkin-plugin.svg?branch=master)](https://travis-ci.org/racodond/sonar-gherkin-plugin)
[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/hhh9gsp77hatvai1/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-gherkin-plugin/branch/master)


## Description
This [SonarQube](http://www.sonarqube.org) plugin analyzes [Cucumber Gherkin feature files](https://cucumber.io/docs/reference#gherkin) and:

 * Computes metrics: lines of code, number of scenarios, etc.
 * Checks various guidelines to find out potential bugs and code smells through more than [40 checks](http://sonarqube.racodond.com/coding_rules#languages=gherkin)
 * Provides the ability to write your own checks


## Usage
1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
1. Install the Cucumber Gherkin plugin by a [direct download](https://github.com/racodond/sonar-gherkin-plugin/releases). The  latest version is compatible with SonarQube 5.6+.
1. Install your [favorite scanner](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.)
1. [Analyze your code](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis)


### Maven
It is likely that your feature files are not located in source code directories but in test directories. By default, SonarQube doesn't analyze those test directories. Thus, you have to explicitly tell SonarQube to also analyze the test directories containing your feature files.

Let's say that the structure of your project is:
```                
pom.xml
src
  |-- main
        |-- java
        |-- resources
  |-- test
        |-- java
        |-- resources
              |-- features
                    |-- my-feature.feature
                    |-- my-other-feature.feature
                                   
                
```         

In you POM file, you would need to add:
```             
<properties>
  <sonar.sources>pom.xml,src/main/java,src/main/resources,src/test/resources/features</sonar.sources>
</properties>  
```


## Custom Checks
You're thinking of new valuable rules? Version 1.0 or greater provides an API to write your own custom checks.
A sample plugin with detailed explanations is available [here](https://github.com/racodond/sonar-gherkin-custom-rules-plugin).
If your custom rules may benefit the community, feel free to create a pull request in order to make the rule available in the Cucumber Gherkin analyzer.

You're thinking of new rules that may benefit the community but don't have the time or the skills to write them? Feel free to create an [issue](https://github.com/racodond/sonar-gherkin-plugin/issues) for your rules to be taken under consideration.


## Contributing
Any contribution is more than welcome!
 
You feel like:
* Adding a new check? Just [open an issue](https://github.com/racodond/sonar-gherkin-plugin/issues/new) to discuss the value of your check. Once validated, code, don't forget to add a lot of unit tests and open a PR.
* Fixing some bugs or improving existing checks? Just open a PR.


## Metrics

### Statements
Number of steps.

### Functions
Number of scenarios and scenario outlines.

### Classes
Number of features.


## Available Rules

* "FIXME" tags should be handled
* "TODO" tags should be handled
* All comments should be formatted consistently
* And and But prefixes should be used instead of redundant Given/When/Then prefixes
* Byte Order Mark (BOM) should not be used for UTF-8 files
* Common Given steps should be added to Background
* Duplicated steps should be removed
* End-line characters should be consistent
* Examples data tables should contain data at least two data rows
* Features should be written in the same language
* Features should have a description
* Features should have a name
* Features should have a unique name
* Features should not contain too many scenarios
* Features that do not define any scenario should be removed
* File names should comply with a naming convention
* Files should contain an empty new line at the end
* Files that do not define any feature should be removed
* Given steps should follow a regular expression
* Given/When/Then steps should be defined in the right order
* Lines should not end with trailing whitespaces
* Missing data table columns should be added
* Non-Given steps should be moved out of Background
* Only tags from the whitelist should be used
* Regular expression on comment
* Scenarios should define at least one of each Given/When/Then step type
* Scenarios should have a name
* Scenarios should have a unique name
* Scenarios should not contain too many steps
* Scenarios that do not define any step should be removed
* Source code should be properly indented
* Spelling mistakes should be fixed
* Star (*) step prefixes should not be used
* Step sentences should not be too long
* Steps of unknown type should not be used
* Tabulation characters should not be used
* Tags should be defined at the right level
* Tags should comply with a naming convention
* Tags should not be set on Examples
* Then steps should follow a regular expression
* There should be one single When step per scenario
* Unused variables should be removed
* Useless tags should be removed
* When steps should follow a regular expression
* Wording should remain at business level
