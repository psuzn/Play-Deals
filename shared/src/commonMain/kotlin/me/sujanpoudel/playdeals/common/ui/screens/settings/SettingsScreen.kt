package me.sujanpoudel.playdeals.common.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.ChooseThemeDialog
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.Footer
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

  var showAppearanceModeDialog by remember { mutableStateOf(false) }

  Scaffold(title = Strings.SETTINGS) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      SettingItem(
        title = "Appearance",
        description = "Choose your light ot dark theme preference",
        onClick = {
          showAppearanceModeDialog = true
        },
      ) {
        Icon(appearanceMode.icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
      }

      SettingItem(
        title = "Don't miss any deals",
        description = "Get notification for all new app deals",
        onClick = { viewModel.setNewDealNotificationEnabled(newDealNotification.not()) },
      ) {
        Switch(
          checked = newDealNotification,
          onCheckedChange = {
            viewModel.setNewDealNotificationEnabled(newDealNotification.not())
          },
        )
      }

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

    ChooseThemeDialog(
      showAppearanceModeDialog,
      closeRequest = { showAppearanceModeDialog = false },
      setAppearanceMode = viewModel::setAppearanceMode,
    )
  }
}
