package me.sujanpoudel.playdeals.common

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import me.sujanpoudel.playdeals.common.pushNotification.AndroidNotificationManager
import me.sujanpoudel.playdeals.common.pushNotification.LocalNotificationManager
import me.sujanpoudel.playdeals.common.pushNotification.LocalPushNotificationPermissionManager

@Composable
fun PlayDealsAppAndroid() {
  val permissionManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    AndroidPermissionManager.rememberPermissionManager(Manifest.permission.POST_NOTIFICATIONS)
  } else {
    PermissionManager.ALWAYS_GRANTED_PERMISSION
  }

  val context = LocalContext.current

  val notificationManager = remember { AndroidNotificationManager(context) }

  CompositionLocalProvider(
    LocalPushNotificationPermissionManager provides permissionManager,
    LocalNotificationManager provides notificationManager,
  ) {
    PlayDealsApp()
  }
}
