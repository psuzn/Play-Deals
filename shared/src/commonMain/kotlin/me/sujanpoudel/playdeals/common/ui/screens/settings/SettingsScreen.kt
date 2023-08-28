package me.sujanpoudel.playdeals.common.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.AppearanceModeSetting
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
  val newDealNotification by viewModel.newDealNotification.collectAsState()
  val developerModeEnabled by viewModel.developerModeEnabled.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  Scaffold(title = Strings.settings) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      AppearanceModeSetting(appearanceMode, viewModel::setAppearanceMode)

      SettingItem(
        title = Strings.dontMissDeal,
        description = Strings.dontMissDealDescription,
        onClick = { viewModel.setNewDealNotificationEnabled(newDealNotification.not()) },
      ) {
        Switch(
          checked = newDealNotification,
          onCheckedChange = {
            viewModel.setNewDealNotificationEnabled(newDealNotification.not())
          },
        )
      }

      LanguageSetting(appLanguage, viewModel::setAppLanguage)

      Spacer(Modifier.weight(1f))

      Footer(
        onClick = { viewModel.setDeveloperModeEnabled(developerModeEnabled.not()) },
        color = if (developerModeEnabled) {
          MaterialTheme.colorScheme.primary
        } else {
          MaterialTheme.colorScheme.onBackground
        },
      )
    }
  }
}
