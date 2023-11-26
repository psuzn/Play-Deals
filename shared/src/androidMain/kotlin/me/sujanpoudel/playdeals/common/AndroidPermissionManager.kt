package me.sujanpoudel.playdeals.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
class AndroidPermissionManager
  private constructor(
    private val context: Context,
    private val state: PermissionState,
  ) : PermissionManager {

    override val permissionState: State<PermissionStatus> = derivedStateOf {
      when (state.status) {
        is com.google.accompanist.permissions.PermissionStatus.Denied -> if (state.status.shouldShowRationale) {
          PermissionStatus.Denied
        } else {
          PermissionStatus.NotAsked
        }

        com.google.accompanist.permissions.PermissionStatus.Granted -> PermissionStatus.Granted
      }
    }

    override fun askForPermission() = state.launchPermissionRequest()

    override fun showRationale() = context.openAppSettingScreen()

    companion object {
      @Composable
      fun rememberPermissionManager(permission: String): PermissionManager {
        val state = rememberPermissionState(permission = permission)
        val context = LocalContext.current
        return remember {
          AndroidPermissionManager(context, state)
        }
      }
    }
  }

private fun Context.openAppSettingScreen() {
  val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
  intent.data = Uri.parse("package:$packageName")
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  startActivity(intent)
}
