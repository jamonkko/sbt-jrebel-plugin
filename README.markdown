sbt-jrebel-plugin
=================

**This is a fork of https://github.com/Gekkio/sbt-jrebel-plugin
 to add SBT 1.x support since there is no plans for original plugin to get it (see more https://github.com/Gekkio/sbt-jrebel-plugin/issues/7)**

## Introduction

sbt-jrebel-plugin is a plugin for [Simple Build Tool](http://www.scala-sbt.org) v1 that generates configuration files (rebel.xml) for [JRebel](http://www.zeroturnaround.com/jrebel/). A rebel.xml is not always required but is recommended because if you don't have one, JRebel cannot understand the layout of your project and might fail to reload changes. You also cannot reload changes from separate projects.

**Supported SBT versions: 1.X**
For older sbt versions please use the original plugin: https://github.com/Gekkio/sbt-jrebel-plugin

## Features

+ Generates rebel.xml, so it's similar to javarebel-maven-plugin in the Maven world
+ Cross-project change reloading

_Default behaviour_

+ Disables itself if SBT isn't run with JRebel agent enabled
+ Writes rebel.xml as a managed resource which is automatically added to classpath *and artifacts*

__You should always disable sbt-jrebel-plugin when publishing artifacts somewhere else than locally. Otherwise your artifacts will include rebel.xml files__

## Usage

**Make sure you run sbt with JRebel agent enabled**

Compile (or download the jar) and save it locally under [project]/lib folder

Add the plugin declaration to project/plugins.sbt:

	addSbtPlugin("fi.jamonkko.sbtplugins" % "sbt-jrebel-plugin_2.12" % "1.0.0" from s"file:///${file("lib").getCanonicalPath}/sbt-jrebel-plugin_2.12-1.0.0.jar")

Then include the plugin settings in your project definition:

	seq(JRebelPlugin.jrebelSettings: _*)

If you are using [xsbt-web-plugin](https://github.com/earldouglas/xsbt-web-plugin) and want to reload web resources, also add this:

*xsbt-web-plugin >= 2.0:*

	jrebelWebLinks += (sourceDirectory in Compile).value / "webapp"

Available settings are:

	jrebelClasspath, jrebelEnabled, jrebelRebelXml, jrebelWebLinks

### How do I ...?

#### Disable rebel.xml generation

Project definition:
`jrebelEnabled := false`

or in SBT console:
`set jrebelEnabled := false`

#### Force rebel.xml generation (regardless of whether you run SBT with JRebel enabled)

Project definition:
`jrebelEnabled := true`

or in SBT console:
`set jrebelEnabled := true`

## Cross-project change reloading

Let's say you have two projects, MyLib which is a library project and MyWebApp which is a webapp. These two are completely separate and differently versioned projects. If you have sbt-jrebel-plugin enabled for both projects, you can make changes in MyLib while developing MyWebApp and have them reloaded instantly.

+ Deploy MyLib with `sbt publish-local`. The package includes a rebel.xml file that contains the absolute paths to your MyLib project directories.
+ Update MyWebApp dependencies with `sbt update`. MyWebApp now uses the MyLib package that has a rebel.xml.
+ Run MyWebApp and enable continuous webapp compilation (for example I use `sbt jetty-run "~  prepare-webapp"`)
+ In a separate terminal, start continuous compilation for MyLib (for example, `sbt ~ compile`).
+ Change a class in MyLib
+ Notice that the change is visible in MyWebApp (if the change is reloadable using JRebel). Voilá!

*Do not share rebel.xml files because by default they contain absolute paths which are computer-specific!*
