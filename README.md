# ActionSheet for Android


[![Download](https://api.bintray.com/packages/aromajoin/maven/actionsheet-android/images/download.svg) ](https://bintray.com/aromajoin/maven/actionsheet-android/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ActionSheet%20for%20Android-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5607)  


**A small library which creates iPad-style ActionSheet for Android apps**  

![Screenshots](https://raw.githubusercontent.com/aromajoin/actionsheet-android/master/screenshots/demo.gif)  

# Table of Contents
1. [Download](#download)
2. [Usage](#usage)


## Download  

The Gradle dependency is available via jCenter.  
```gradle
    compile 'com.aromajoin.library:actionsheet:0.0.2'
```
## Usage


```java

    // Sets it up
    ActionSheet actionSheet = new ActionSheet(context);
    actionSheet.setTitle(title);
    actionSheet.setSourceView(anchorView);

    // Adds as many actions as you need...
    actionSheet.addAction(actionTitle, actionStyle, actionListener);

    // Shows it. Done.
    actionSheet.show();
```  

If you don't like default colors, add these following to your styles.xml, under *your app's theme*.
```xml
  <style name="YourAppTheme">
        <!-- Other properties .... -->

        <!-- Customize ActionSheet style -->
        <item name="asWidth">xdp</item>
        <item name="asTitleColor">color</item>
        <item name="asDefaultColor">color</item>
        <item name="asDestructiveColor">color</item>
        <item name="asTitleTextSize">xsp</item>
        <item name="asActionTextSize">xsp</item>
    </style>
```  

Please check out sample project if you need more details.

-----  
## License  

The Apache License (Apache)

    Copyright (c) 2017 Aromajoin Corporation

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
