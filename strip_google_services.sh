#!/bin/sh

sed -i'.bak' -e '5d;17,19d;' androidApp/build.gradle.kts
sed -i'.bak' -e '26,34d;' androidApp/src/androidMain/AndroidManifest.xml
rm androidApp/src/androidMain/kotlin/me/sujanpoudel/playdeals/FcmService.kt

sed -i'.bak' -e '5d;14d;18d;' shared/src/androidMain/kotlin/me/sujanpoudel/playdeals/common/pushNotification/AndroidNotificationManager.kt
sed -i'.bak' -e '79,81d;' shared/build.gradle.kts
sed -i'.bak' -e '16d;' build.gradle.kts
