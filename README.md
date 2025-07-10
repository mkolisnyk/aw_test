# Technical assesment

## Overview

The repository contains technical assessment implementation which is 
about testing framework implementation for [Sauce Labs sample Android application](https://github.com/saucelabs/sample-appmobile/
releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk).

It includes the core framework as well as several sample tests implementation.

## Assumptions and restrictions

Due to time and resources constraints current solution has limited capabilities, but it can be extended.

The following is the list of assumptions and restrictions:
1. The solution is implemented for the Android app version, however, the framework itself reserves the possibility to add iOS support. 
2. Tests were developed and tested on real device. Base emulators support is not included, however, emulators like [Genymotion](https://www.genymotion.com/) can work the same way.
3. Test device should be connected via USB (or any other way it's visible by ADB) and should be of single instance, however, multiple device support is included

## Tech Stack

This sample covers Android application version, however, the framework can be extended to support iOS version as well.

Here are major technologies/engines which were involved:

| Technology/engine | Version | Description                                                                                   |
|-------------------|---------|-----------------------------------------------------------------------------------------------|
| Java              | 21      | Major programming platform as well as the programming language                                |
| Appium            | 2.19    | Core automation driver                                                                        |
| Appium client     | 9.5.0   | Major API for interacting with Appium                                                         |
| Gradle            | 8.10.2  | Build and dependency management engine                                                        |
| Android SDK       | 35      | The set of libraries and utilities needed by Appium to interact with Android apps and devices |

## Installation Instructions

Overall preparation requires several stages which cover:
* Java setup
* Android SDK setup
* Appium setup
* Test device setup

### Java Setup

Download and install Java according to [installation instructions](https://www.java.com/en/download/help/download_options.html)
for the operating system which is in use.

You can use either installation package or [SDK manager](https://sdkman.io/install)

### Android SDK setup

In order to interact with Android devices and application, we need to setup Android SDK.
Major instructions can be found on [Appium documentation page](https://appium.io/docs/en/latest/quickstart/uiauto2-driver/#android-sdk).
There are options for using Android studio or command line tools. Both options are valid.

Major setting taken from here is the **ANDROID_HOME** environment variable. It should point to location where
the **platform-tools** folder is located as the framework uses **adb** as the command line access to
the device. This utility should be located at the **$ANDROID_HOME/platform-tools/adb** path (for Windows it would be **%ANDROID_HOME%/platform-tools/adb**).

### Appium Setup

#### Node.js and NPM setup

Appium itself is Node.js based aapplication and major way to install it requires **Node.js** and **NPM** package manager installed.
Here are [detailed instructions](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm) on how to do it.

#### Appium installation 

Appium installation instructions can be found at the [Official Documentation](https://appium.io/docs/en/2.0/quickstart/install/).
Mainly, it's about running the command like:

``` 
npm i --location=global appium
```

This will install Appium as globally accessible application.

#### UiAutomator2 driver installation

In addition to Appium itself, there is a need to install **UiAutomator2** plugin
to interact with Android application. Detailed installation instructions can be found [here](https://appium.io/docs/en/2.0/quickstart/uiauto2-driver/).
Practically, once you have Appium installed, the following command line should be performed:

``` 
appium driver install uiautomator2
```

### Test Device Setup

Current solution is targeted to use real devices or on emulators which are recognised as real devices (e.g. [Genymotion](https://www.genymotion.com/)).
For the device setup, the following steps should be done:
1. [Enable Developer options](https://developer.android.com/studio/debug/dev-options?_gl=1*11j1v8p*_up*MQ..*_ga*MTA4MzYwOTk5Mi4xNzUyMTQ2ODg4*_ga_6HH9YJMN9M*czE3NTIxNDY4ODckbzEkZzAkdDE3NTIxNDY4ODckajYwJGwwJGgxODkyNzgzMzQ4#enable)
2. [Enable debugging on the device](https://developer.android.com/studio/debug/dev-options?_gl=1*11j1v8p*_up*MQ..*_ga*MTA4MzYwOTk5Mi4xNzUyMTQ2ODg4*_ga_6HH9YJMN9M*czE3NTIxNDY4ODckbzEkZzAkdDE3NTIxNDY4ODckajYwJGwwJGgxODkyNzgzMzQ4#Enable-debugging)
3. [Enable General options](https://developer.android.com/studio/debug/dev-options?_gl=1*11j1v8p*_up*MQ..*_ga*MTA4MzYwOTk5Mi4xNzUyMTQ2ODg4*_ga_6HH9YJMN9M*czE3NTIxNDY4ODckbzEkZzAkdDE3NTIxNDY4ODckajYwJGwwJGgxODkyNzgzMzQ4#general), in particular, **Stay Awake** option can be in use
4. Plug the device via USB, so that it is visible by the ADB tool

## Test Run Instructions

Test run is mainly done in 2 steps:
1. Start Appium
2. Run test suite

The solution is targeted to perform all runs from command line so that the solution is 
compatible with the CI.

Appium can be started using the following command:

```
appium
```

Testing solution operates with the default port of **4723**.

Once Appium is started, we can open **separate console window** and run the following command:

```
gradlew test
```

or for Linux/MacOS:

``` 
./gradlew test
```
