# Play Deals

![Static Badge](https://img.shields.io/badge/License-GPL--v3-brightgreen)
[![Lint and verify](https://github.com/psuzn/App-deals/actions/workflows/lint.yaml/badge.svg?branch=develop)](https://github.com/psuzn/App-deals/actions/workflows/lint.yaml)

![Feature](./media/feature-graphic.jpeg)

| <a href="https://play.google.com/store/apps/details?id=me.sujanpoudel.playdeals">     <img src="media/badge-get-on-google-play.png" width="200" alt="Get it on Google play">   </a> | <a href="https://github.com/psuzn/app-deals/releases/latest">     <img src="media/badge-download-apk.png" width="160" alt="Download Apk">   </a> |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------:|

Play deals is a simple app that aggregates the paid apps that have ongoing deals and discounts, aka you can get the
paid apps free or with discount.

## Download

You can download the app from [play store](https://play.google.com/store/apps/details?id=me.sujanpoudel.playdeals) or
directly download and install the [latest apk](https://github.com/psuzn/app-deals/releases/latest). For other platforms
visit to [release page](https://github.com/psuzn/app-deals/releases/latest) to download the latest built.

### Supported platforms:

- Android
- IOS
- Mac
- Linux
- Windows

## Development

This app is built using this amazing thing
called [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html)
and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
It shares same business logic and UI across all the platform.

### Tools and Libraries Used

- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) to share same codebase/compile to different
  targets.
- [Compose Multiplatform UI](https://www.jetbrains.com/lp/compose-multiplatform/) to build the cross platform UI.
- [Ktor](https://www.jetbrains.com/lp/compose-multiplatform/) for the http/api calls.
- [Kamel](https://github.com/Kamel-Media/Kamel) for loading images.
- [Kodein](https://www.google.com/search?q=kodein&sourceid=chrome&ie=UTF-8) for dependency injection.

### UI Navigation

Rather than using a 3rd party navigation library, I have built a simple navigation package from scratch that fits the
need. This also supports scoped based ViewModels and custom push/pop animations. This package is located
at [`shared/common/navigation`](shared/src/commonMain/kotlin/me/sujanpoudel/playdeals/common/navigation).

### Basic Architecture

This loosely follows mvvm architecture inspired from android's view model.

## TO-DO

**In Priority order** :

- [ ] Add Offline Caches for the apps
- [ ] Push Notifications
- [ ] Add ability to add/request new app deal from app

## License

**GPL V3 License**

Copyright (c) 2023 Sujan Poudel

