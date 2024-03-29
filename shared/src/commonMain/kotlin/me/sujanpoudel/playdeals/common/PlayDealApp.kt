package me.sujanpoudel.playdeals.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import me.sujanpoudel.playdeals.common.di.PrimaryDI
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.navigation.NavGraph
import me.sujanpoudel.playdeals.common.navigation.NavHost
import me.sujanpoudel.playdeals.common.pushNotification.NotificationManager
import me.sujanpoudel.playdeals.common.pushNotification.current
import me.sujanpoudel.playdeals.common.pushNotification.pushNotificationPermissionManager
import me.sujanpoudel.playdeals.common.pushNotification.syncNotificationTopics
import me.sujanpoudel.playdeals.common.strings.LocalAppLanguage
import me.sujanpoudel.playdeals.common.ui.screens.ChangeLogScreen
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.screens.newDeal.NewDealScreen
import me.sujanpoudel.playdeals.common.ui.screens.settings.SettingsScreen
import me.sujanpoudel.playdeals.common.ui.screens.settings.notificationSettings.NotificationSettingsScreen
import me.sujanpoudel.playdeals.common.ui.theme.AppTheme
import org.kodein.di.direct
import org.kodein.di.instance

enum class Screens {
  Home,
  NEW_DEAL,
  SETTINGS,
  NOTIFICATION_SETTING,
  CHANGELOG,
}

private val navGraph = NavGraph {
  destination(Screens.Home) {
    HomeScreen()
  }

  destination(Screens.NEW_DEAL) {
    NewDealScreen()
  }

  destination(Screens.SETTINGS) {
    SettingsScreen()
  }

  destination(Screens.NOTIFICATION_SETTING) {
    NotificationSettingsScreen()
  }

  destination(Screens.CHANGELOG) {
    ChangeLogScreen()
  }

  homePath = Screens.Home
}

@Composable
fun PlayDealsApp() {
  val preferences = remember { PrimaryDI.direct.instance<AppPreferences>() }
  val notificationPermissionManager = PermissionManager.pushNotificationPermissionManager
  val notificationManager = NotificationManager.current

  val appLanguage by preferences.appLanguage.collectAsState()
  val notificationPermissionState by notificationPermissionManager.permissionState

  LaunchedEffect(notificationPermissionState) {
    delay(1000)
    when (notificationPermissionState) {
      PermissionStatus.Granted,
      PermissionStatus.Denied,
      -> return@LaunchedEffect

      PermissionStatus.NotAsked -> notificationPermissionManager.askForPermission()
    }
  }

  LaunchedEffect(notificationManager) {
    notificationManager.syncNotificationTopics(preferences)
  }

  CompositionLocalProvider(
    LocalAppLanguage provides appLanguage,
  ) {
    AppTheme(preferences) {
      Box(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.background),
      ) {
        NavHost(navGraph)
      }
    }
  }
}
