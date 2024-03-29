# Play Deals

[![Static Badge](https://img.shields.io/badge/Android-black?logo=android&logoColor=white&color=%234889f5)](https://play.google.com/store/apps/details?id=me.sujanpoudel.playdeals)
&nbsp;
[![Static Badge](https://img.shields.io/badge/IOS-grey?logo=apple)](https://github.com/psuzn/app-deals/releases/latest)
&nbsp;&nbsp;
[![Static Badge](https://img.shields.io/badge/macOS-black?logo=apple)](https://github.com/psuzn/app-deals/releases/latest)
&nbsp;
[![Static Badge](https://img.shields.io/badge/Windows-green?logo=windows&color=blue)](https://github.com/psuzn/app-deals/releases/latest)
&nbsp;
[![Static Badge](https://img.shields.io/badge/Linux-white?logo=linux&logoColor=white&color=grey)](https://github.com/psuzn/app-deals/releases/latest)
&nbsp;

![Static Badge](https://img.shields.io/badge/License-GPL--v3-brightgreen)
[![CI](https://github.com/psuzn/Play-Deals/actions/workflows/CI.yaml/badge.svg)](https://github.com/psuzn/Play-Deals/actions/workflows/CI.yaml)

![Feature](metadata/en-US/images/featureGraphic.png)

| <a href="https://play.google.com/store/apps/details?id=me.sujanpoudel.playdeals"> <img src="media/badge-get-on-google-play.png" width="200" alt="Get it on Google play"> </a> | <a href="https://f-droid.org/packages/me.sujanpoudel.playdeals"> <img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png" alt="Get it on F-Droid" height="80"> <a/> | <a href="https://github.com/psuzn/app-deals/releases/latest"> <img src="media/badge-get-it-on-github.png" height="80" alt="Get it on GitHub"> </a> |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------:|

Play deals is a simple app that aggregates the paid apps that have ongoing deals and discounts, aka you can get the
paid apps free or with discount.

| <img src="metadata/en-US/images/phoneScreenshots/1.png" width="95%" > | <img src="metadata/en-US/images/phoneScreenshots/3.png"  width="95%" > |
|-----------------------------------------------------------------------|:----------------------------------------------------------------------:|

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
- [Kodein](https://github.com/kosi-libs/Kodein) for dependency injection.
- [SQLDelight](https://github.com/cashapp/sqldelight) to offline cache using sqlite.

### UI Navigation

Rather than using a 3rd party navigation library, I have built a simple navigation package from scratch that fits the
need. This also supports scoped based ViewModels and custom push/pop animations. This package is located
at [`shared/common/navigation`](shared/src/commonMain/kotlin/me/sujanpoudel/playdeals/common/navigation).

### Basic Architecture

This loosely follows mvvm architecture inspired from android's view model.

## TO-DO

**In Priority order** :

- [x] Add Offline Caches for the apps
- [x] Push Notifications
- [ ] Add ability to add/request new app deal from app

## License

**GPL V3 License**

Copyright (c) Sujan Poudel

