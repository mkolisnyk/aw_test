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
4. Due to lack of time and resources, the solution was tested just on a few devices
5. Since the solution is isolated from app development repository, it is assumed that application package is stored in the project root directory (it is provided together with the code), however, it still can be configured.
6. Due to time and resources constraints, the solution assumes local instance of Appium up and running. Remote locations and cloud solutions were not covered, however, their support is about some updates in capabilities. Currently, it uses default port, but it reserves possibility to re-configure it (see Configuration section)

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

## Test Solution Structure and Configuration

The entire solution has the following parts:
* Core framework - the core library wrapping Appium commands around common (not application specific operations)
* Application-specific libraries - the code part which reflects objects and business logic specific to application under test
* Tests - the set of JUnit 5 based classes containing actual tests
* Configuration - additional resources which are responsible for adjusting test runs to specific environment

### Core Framework

The core framework mainly implements Page Object pattern. Also, it has additional abstractions for Control objects
when classes wrap functionality of each separate element and split the logic depending on the nature of the element
(e.g. Edit fields have capability to enter the text which is not available for regular controls).

Also, the framework wraps functionality around handling the driver and configuration instances.

Major packages are:

**com.sample.framework**

Common package for the framework. The package root mainly has classes responsible for wrapping 
the driver functionality as well as dedicated object to load and return configuration parameters.

**com.sample.framework.ui**

Major package wrapping functionality around page and control objects.
By the design the control object wraps the functionality of the specific control (like click, element existence, visibility checks etc).
At the same time page class and all it's extensions wrap functionality related to specific page/view
and additionally serves as the container for control objects.

Using such approach each particular page class looks like as follows:

```java
package com.sample.tests.pages;

import org.openqa.selenium.WebDriver;

import com.sample.framework.ui.FindBy;
import com.sample.framework.ui.Page;
import com.sample.framework.ui.controls.Control;
import com.sample.framework.ui.controls.Edit;

public class CheckOutPage extends Page {

    @FindBy(locator = "//android.widget.EditText[@content-desc=\"test-First Name\"]")
    public Edit editFirstName;

    @FindBy(locator = "//android.widget.EditText[@content-desc=\"test-Last Name\"]")
    public Edit editLastName;

    @FindBy(locator = "//android.widget.EditText[@content-desc=\"test-Zip/Postal Code\"]")
    public Edit editZipCode;
    
    @FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CANCEL\"]")
    public Control buttonCancel;

    @FindBy(locator = "//android.view.ViewGroup[@content-desc=\"test-CONTINUE\"]")
    public Control buttonContinue;

    public CheckOutPage(WebDriver driverValue) {
        super(driverValue);
    }

    public CheckOutPage fillFormData(String firstName, String lastName, String zipCode) throws Exception {
        editFirstName.setText(firstName);
        editLastName.setText(lastName);
        editZipCode.setText(zipCode);
        return hideKeyboard(CheckOutPage.class);
    }
}

```

**com.sample.framework.utils**

Contains additional util classes which are not specific to the application under test but are
not related to UI elements and abstractions around them. In current repository, this package contains class responsible for various command line operations
to install/uninstall/clear application.

### Application-specific libraries

This group of packages contain classes and other entities which are specific to application under test.

**com.sample.tests.controls**

Contains controls which are extensions of standard controls, but they reflect some application specifics.
In case of current solution the Add/remove toggle button from the list of products is the good candidate.
Generally, it is regular control. But it has overridden **getText** method which is
specific for current application under test.

**com.sample.tests.helpers**

Contains application under test specific utilities. In particular, it contains class responsible for application under test
start/stop/reset/uninstallation.

**com.sample.tests.pages**

Contains page classes for application under test. Each class has definition of controls
as well as it groups common operations around the page. For such page methods, it's typical to return the object corresponding to the page which should appear after the set of operations.
This way chained operations are possible.

### Tests

All available tests are located at the **com.sample.tests.junit** package. These are regular JUnit 5 tests, and they can be run from IDE as well as from command line.
Due to the use of Page Object pattern, it is possible to write chain operations in the form like this:

```java
@Test
public void testStandardUserMultipleCheckout() throws Exception {
    this.pageLogin.login()
            .selectProduct("Sauce Labs Backpack")
            .selectProduct("Sauce Labs Fleece Jacket")
            .selectProduct("Sauce Labs Onesie")
            .buttonCart.click(CartPage.class)
            .buttonCheckout.click(CheckOutPage.class)
            .fillFormData(checkoutFirstName, checkoutLastName, checkoutZipCode)
            .buttonContinue.click(CheckOutOverviewPage.class)
            .buttonFinish.click(CheckOutCompletePage.class)
            .buttonBackHome.click(ProductsPage.class);
} 
```
Here each particular method returns the page object which should appear after previous operation.
**NOTE:** each time the page is returned, it is additionally verified that expected page is actually displayed.

It is not the only available writing style. It's always possible to come back to procedure writing style. 

### Configuration

All the configuration is located at the **config.properties** file and it is represented as a set of **<name>=<value>** pairs.
Current content of the configuration file reflects minimal local configuration and generally is not supposed to be changed unless some specific Appium configuration (in particular port) is in use

The properties in use are currently as follows:

| Property        | Description                                                                                                                                       |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| app_path        | Path to application under test package file                                                                                                       |
| udid            | The UDID of the connected device. If the only device is connected, this parameter is not necessary                                                |
| commandTimeout  | The value of Appium command timeout capability                                                                                                    |
| port            | The port the Appium listens to. Currently set to the default value of 4723                                                                        |
| appPackage      | The package name of the application under test. It is needed for install/uninstall/cleanup operations. Currently set to **com.swaglabsmobileapp** |
| timeout         | Wait timeout. The maximum time to wait for element to become at some certain state (exist, visible, enabled etc).                                 |
| pages_package   | Indicates the package, where page classes are located. Should be constant and currently it is **com.sample.tests.pages**                          |

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
