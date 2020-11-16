[![Build Status](https://api.travis-ci.org/alphamu/AnimatedEditText.svg?branch=master)](https://travis-ci.org/alphamu/AnimatedEditText)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AnimatedEditText-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3304) 

## Fix

I fixed an issue that made it so the text would disappear when set to wrap content.

# AnimatedEditText for Android

This repository contains `AnimatedEditText` and `TextDrawable` all of which
extend the behaviour of `EditText` and implement features that are otherwise not available.

**NOTE:** [`PinEntryEditText` has been moved to it's own repo on GitHub](https://github.com/alphamu/PinEntryEditText).

![pop in animation](http://i.giphy.com/x1AsZJypT6rmw.gif) 

## Features

### AnimatedEditText

- Text animates in as typed (4 animations available).
- Text animates out when cleared using `setText(null)` or `setText("")`.
- Smooth forward movement of cursor (API 16+).
- Smooth backward movement of cursor when clear is called (API 16+, see known issues).
- Lets you specify any character to be used as a mask for input.

### PinEntryEditText

[PinEntryEditText has been moved to it's own repo on GitHub](https://github.com/alphamu/PinEntryEditText).

### Others

- TextDrawable which allows you to set and use text as a drawable.


## Usage

Below is a fast guide to getting started. However, if you need to read about these widgets in
details. Read more about [AnimatedEditText](https://medium.com/@ali.muzaffar),
[PinEntryEditText](https://medium.com/@ali.muzaffar/building-a-pinentryedittext-in-android-5f2eddcae5d3#.tka93qm3d),
[TextDrawable](https://medium.com/@ali.muzaffar/textdrawable-to-display-emojis-and-unicode-characters-in-android-35614168b8ad#.21ukwskwr).

## STEP-1

### Gradle

If using **Jetpack** use version [![Download](https://api.bintray.com/packages/alphamu/customwidgets/AnimatedEditText/images/download.svg)](https://bintray.com/alphamu/customwidgets/AnimatedEditText/_latestVersion)

    dependencies {
        implementation ('com.alimuzaffar.lib:animated-edit-text:2.0.2') {
            // Exclude is only needed if you already have the library
            // mentioned below as a dependency in your app
            exclude group: 'androidx.appcompat', module: 'appcompat'
        }
    }

If not using Jetpack use version 1.x [ ![Download](https://api.bintray.com/packages/alphamu/customwidgets/AnimatedEditText/images/download.svg?version=1.2.3) ](https://bintray.com/alphamu/customwidgets/AnimatedEditText/1.2.3/link)

    dependencies {
        implementation ('com.alimuzaffar.lib:animated-edit-text:1.2.3') {
            // Exclude is only needed if you already have the library
            // mentioned below as a dependency in your app
            exclude group: 'com.android.support', module: 'appcompat-v7'
        }
    }

**Note:** You don't need to exclude all the support groups. However, it's a good idea to do this
as your code is likely to use a different version from the library.

## STEP-2

### Setup AnimatedTextView and PinEntryView in your layout

```xml
<com.alimuzaffar.lib.widgets.AnimatedEditText
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Animate pop in"
    android:inputType="textNoSuggestions" //recommended when typing text to avoid autocomplete.
    app:animationType="popIn|fromBottom|fromRight|fromMiddle|none" //Optional, default popIn
    app:animateCursor="true|false" //Optional, default true
    app:animateTextClear="true|false" /> //Optional, default true
```

### Use in your code

Use in your code like you would use a regular EditText
```java
EditText regular = (AnimatedEditText) findViewById(R.id.txt_regular);
if (regular != null) {
    String input = regular.getText().toString();
}
```

## Effects :

1. PopIn (default)
2. Bottom Up
3. In from right
4. In from middle

## Demo

### Pop-in animation

![pop in animation](http://i.giphy.com/x1AsZJypT6rmw.gif)

### Bottom Up Animations

![bottom up animation](http://i.giphy.com/DwawaNNbE3UKQ.gif)

### In from right

![animate in from right](http://i.giphy.com/Nzd38Q57CJ5zq.gif)

### In from middle

![animate in from middle](http://i.giphy.com/8WKKm4nTM3N60.gif)

You can see a YouTube video of all the animations here: [YouTube video](https://www.youtube.com/watch?v=VW6vFzniehU)

## Issues :

- Only works for single line input, the second the line has to wrap, the animation breaks.
- Animation will work best if `android:inputType="textNoSuggestions"` is set.
- Animations are only triggered when adding to the end of the string.
- Only `android:gravity="left|right|center_horizontal"` is supported.
- Smooth back movement of cursor when text is cleared doesn't work too well for `android:gravity="center_horizontal"`
- No RTL language support.
- Animate in from middle doesn't work properly except from `android:gravity="left"`
- Not all features of EditText will work
- Using some Unicode characters as masks causes the cursor to lose position. If this happens, I recommend setting cursor visibility to false or setting textPassword or numberPassword as the input type for the fields which improves the situation but doesn't really solve it in all cases.

License
=======

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
