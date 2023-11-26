package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface PermissionManager {

  val permissionState: State<PermissionStatus>
  fun askForPermission()

  fun showRationale()

  companion object {
    val NONE = object : PermissionManager {
      override val permissionState: State<PermissionStatus> = mutableStateOf(PermissionStatus.NotAsked)

      override fun askForPermission() {}

      override fun showRationale() {}
    }

    val ALWAYS_GRANTED_PERMISSION = object : PermissionManager {
      override val permissionState: State<PermissionStatus> = mutableStateOf(PermissionStatus.Granted)

      override fun askForPermission() {}

      override fun showRationale() {}
    }
  }
}

enum class PermissionStatus {
  Granted,
  Denied,
  NotAsked,
}
