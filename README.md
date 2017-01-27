SonarQube Cucumber Gherkin Analyzer
===================================

[![Build Status](https://api.travis-ci.org/racodond/sonar-gherkin-plugin.svg?branch=master)](https://travis-ci.org/racodond/sonar-gherkin-plugin)
[![AppVeyor Build Status](https://ci.appveyor.com/api/projects/status/hhh9gsp77hatvai1/branch/master?svg=true)](https://ci.appveyor.com/project/racodond/sonar-gherkin-plugin/branch/master)
[![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.racodond.sonarqube.plugin.gherkin:gherkin)](https://sonarqube.com/overview?id=com.racodond.sonarqube.plugin.gherkin%3Agherkin)

## Description
This [SonarQube](http://www.sonarqube.org) plugin analyzes [Cucumber Gherkin feature files](https://cucumber.io/docs/reference#gherkin) and:

 * Computes metrics: lines of code, number of scenarios, etc.
 * Checks various guidelines to find out code smells and potential bugs through more than [40 checks](http://sonarqube.racodond.com/coding_rules#languages=gherkin)
 * Provides the ability to write your own checks
 
## Demo
 * [Simple Demo project](http://sonarqube.racodond.com/dashboard/index?id=gherkin-sample-project)
 * Real life project: [Diaspora](http://sonarqube.racodond.com/dashboard?id=diaspora)

## Usage
1. [Download and install](http://docs.sonarqube.org/display/SONAR/Setup+and+Upgrade) SonarQube
1. Install the Cucumber Gherkin analyzer either by a [direct download](https://github.com/racodond/sonar-gherkin-plugin/releases) or through the [Update Center](http://docs.sonarqube.org/display/SONAR/Update+Center).
1. [Install your favorite analyzer](http://docs.sonarqube.org/display/SONAR/Analyzing+Source+Code#AnalyzingSourceCode-RunningAnalysis) (SonarQube Scanner, Maven, Ant, etc.) and analyze your code. Note that Java 8 is required to run an analysis.

Plugin versions and compatibility with SonarQube versions: [http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix](http://docs.sonarqube.org/display/PLUG/Plugin+Version+Matrix)

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

## Metrics

### Statements
Number of steps.

### Functions
Number of scenarios and scenario outlines.

### Classes
Number of features.


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
