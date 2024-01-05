package me.sujanpoudel.playdeals.common.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.pushNotification.NotificationManager
import me.sujanpoudel.playdeals.common.pushNotification.current
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.common.rememberTextTitle
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.AppearanceModeSetting
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.CurrencySetting
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.Footer
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.LanguageSetting
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.SettingItem
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.asUITheme
import me.sujanpoudel.playdeals.common.viewModel.viewModel

val AppearanceMode.icon: ImageVector
  @Composable
  get() = when (asUITheme()) {
    UIAppearanceMode.DARK -> Icons.Outlined.DarkMode
    UIAppearanceMode.LIGHT -> Icons.Filled.LightMode
    UIAppearanceMode.BLACK -> Icons.Filled.DarkMode
  }

@Composable
fun SettingsScreen() {
  val viewModel = viewModel<SettingsScreenViewModel>()

  val appearanceMode by viewModel.appearanceMode.collectAsState()
  val developerModeEnabled by viewModel.developerModeEnabled.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()
  val forexUpdatedAt by viewModel.forexUpdatedAt.collectAsState()
  val preferredCurrency by viewModel.preferredConversion.collectAsState()
  val forexRates by viewModel.forexRates.collectAsState()
  val navigator = Navigator.current
  val notificationManager = NotificationManager.current

  Scaffold(title = rememberTextTitle(Strings.settings)) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      if (notificationManager != NotificationManager.NONE) {
        SettingItem(
          title = Strings.pushNotification,
          description = Strings.pushNotificationDescription,
          onClick = { navigator.push(Screens.NOTIFICATION_SETTING) },
          rightAction = {
            Icon(Icons.Default.ChevronRight, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
          },
        )
      }

      AppearanceModeSetting(appearanceMode, viewModel::setAppearanceMode)

      LanguageSetting(appLanguage, viewModel::setAppLanguage)

      CurrencySetting(preferredCurrency, forexRates, viewModel::setPreferredCurrency)

      Spacer(Modifier.weight(1f))

      Footer(
        onClick = { viewModel.setDeveloperModeEnabled(true) },
        forexUpdatedAt = forexUpdatedAt,
        color = if (developerModeEnabled) {
          MaterialTheme.colorScheme.primary
        } else {
          MaterialTheme.colorScheme.onBackground
        },
      )
    }
  }
}
