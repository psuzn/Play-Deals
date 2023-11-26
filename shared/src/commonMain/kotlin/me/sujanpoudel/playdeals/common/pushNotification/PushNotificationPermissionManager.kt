package me.sujanpoudel.playdeals.common.pushNotification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import me.sujanpoudel.playdeals.common.PermissionManager

val LocalPushNotificationPermissionManager = compositionLocalOf {
  PermissionManager.NONE
}

val PermissionManager.Companion.pushNotificationPermissionManager
  @Composable
  @ReadOnlyComposable
  get() = LocalPushNotificationPermissionManager.current
