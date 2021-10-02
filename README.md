# Social-Network
Social Network is a social media app, where a user can publicly talk about any particular topic be it via text or voice chat.
# About
It simply uses firebase as a backend and performs authentication and storage operations on firebase itself, a user simply authenticates his/her google account and gets inside the whole network. Further, the user can post about something in text format or image format. Also, a user has a profile and can view others people profile too can follow others etc.
# Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.
- [Pager 3](https://developer.android.com/reference/kotlin/androidx/paging/Pager) - For Pagination of data
## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
# App 
| Home | Profile |
|:-:|:-:|
| ![Third](https://user-images.githubusercontent.com/80918746/135667016-a6ba8901-5f78-4c4c-9f68-504365b2b5b9.png) | ![Fourth](https://user-images.githubusercontent.com/80918746/135667060-9d6a3b9e-5285-40ea-aec4-f7ddcb3eed64.png) | 
| InApp Camera | Image Post | 
| ![Third](https://user-images.githubusercontent.com/80918746/135727192-6e8bc0c8-9fc1-4622-8c05-af9449bf814d.png) | ![Fourth](https://user-images.githubusercontent.com/80918746/135667277-30199145-7d6b-4617-9e31-88026c239779.png) | 
| Auth | Auth | 
| ![First](https://user-images.githubusercontent.com/80918746/135666196-46d4e5f1-4bc3-4b8a-89f4-a592d0c1425c.png) | ![Sec](https://user-images.githubusercontent.com/80918746/135666256-4aee9312-d1c5-46ff-801c-b7e0e2e57a82.png) |
 

 

