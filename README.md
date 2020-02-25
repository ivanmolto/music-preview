# CORNERMUSIC APP

CornerMusic is a Kotlin Android application to demonstrate how to create a solid and extensible modern application.

CornerMusic displays a list of songs from iTunes and previews the selected track.


## DEVELOPMENT SETUP
The app is written entirely in Kotlin and uses the Gradle build system.

To build the app, use "Import Project" in Android Studio
(a version of Android Studio >= 3.5.3 is required).

Open it in Android Studio.

## API
The iTunes Search API is used in this application. It gives us all the required use cases of an API without requiring authorization.
You can find the public API Docs for getting data [here](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/ ).

## ARCHITECTURE
CornerMusic is built around Android Architecture Components,  MVVM architecture, and S.O.L.I.D principles.

I have kept logic away from Activities and Fragments. All logic is placed into ViewModels.
Data is observed using LiveData and Data Binding is used to bind UI components in layouts to the app data sources.

The application does network requests (HTTP) through Retrofit, OkHTTP and GSON.
The data loaded is saved to SQL database (Room).
Glide is used for image loading and Timber is used for logging.

The single-activity architecture contributes to use the Navigation component to manage fragment operations.

Dagger 2 is used for dependency injection.


## LIBRARIES USED
Foundation - Components for core system capabilitites:
* [Android KTX](https://developer.android.com/kotlin/ktx.html ) to write concise and idiomatic Kotlin code
* [AppCompat](https://developer.android.com/topic/libraries/support-library/packages.html#v7-appcompat ) to degrade gracefully on older Android versions.
* Test - an Android testing framework for unit and runtime UI tests.

Architecture - to design a robust, testable, a maintainable app:
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/ ) to bind observable data to UI using a declarative format rather than programmatically.
* [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle ) to manage activity and fragment lifecycles.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata ) to notify views when underling database changes.
* [Navigation](https://developer.android.com/guide/navigation/ ) to handle navigation
* [Room](https://developer.android.com/topic/libraries/architecture/room ) - a persistence library providing an abstraction layer over SQLite.
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel ) - designed to store and manage UI - related data in a lifecycle conscious way.

UI - UI Components in the app:
* [Fragment](https://developer.android.com/guide/components/fragments ) - a unit of a composable UI.
* [Layout](https://developer.android.com/guide/topics/ui/declaring-layout ) widgets.


## OTHER LIBRARIES AND PLUGINS
* [Dagger 2](https://dagger.dev/ ) to create and manage the graph of dependencies.
* [Retrofit](https://github.com/square/retrofit ) - a type-safe HTTP client for Android and Java.
* [Glide](https://github.com/bumptech/glide ) to load and display an image from a web URL.
* [Timber](https://github.com/JakeWharton/timber ) - a library for logging in your app.
* [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data ) to pass data between fragments.


## COROUTINES
This application uses Kotlin coroutines for long-running tasks.

## TEST DEPENDENCIES
* [UIAutomator](https://developer.android.com/training/testing/ui-automator )- a UI testing framework.
* [Espresso](https://developer.android.com/training/testing/espresso ) to write Android UI tests.
* [Robolectric](https://github.com/robolectric/robolectric ) - unit testing framework for Android.
* [JUnit](https://github.com/junit-team/junit4 ) to write repeatable tests.
* [Mockito](https://github.com/nhaarman/mockito-kotlin ) - for unit tests written in Java.


## CODE STYLE
[ktlint](https://ktlint.github.io/ ) has been used as a linter to check the code clarity and community conventions.
Not included in the app to avoid coinfiguration issues (instructions out of the scope of this project).
Excluding:
* Wildcard imports.
* Imports ordered in lexicographic order.


## TODO
* Tests
* Documentation via Dokka

Note: Crashlytics has not been added to the challenge as it requires Firebase.
The project "as is" does not require any configuration steps.


## LICENSE
```
Copyright 2020 Ivan Molto All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
The CornerMusic source code is open source, and may be redistributed under the terms specified in this LICENSE.

All trademarks and registered trademarks are the properties of their respective owners.
It's not accepted any liability for the use of this application.
