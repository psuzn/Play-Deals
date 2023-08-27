package me.sujanpoudel.playdeals.common.ui.screens.settings

import kotlinx.coroutines.flow.StateFlow
import me.sujanpoudel.playdeals.common.AppPreferences
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.viewModel.ViewModel

class SettingsScreenViewModel(
  private val appPreferences: AppPreferences,
) : ViewModel() {

  val appearanceMode: StateFlow<AppearanceMode> = appPreferences.appearanceMode
  val newDealNotification: StateFlow<Boolean> = appPreferences.newDealNotification
  val developerModeEnabled: StateFlow<Boolean> = appPreferences.developerMode

  fun setAppearanceMode(mode: AppearanceMode) {
    appPreferences.appearanceMode.update(mode)
  }

  fun setDeveloperModeEnabled(enabled: Boolean) {
    appPreferences.developerMode.update(enabled)
  }

  fun setNewDealNotificationEnabled(enabled: Boolean) {
    appPreferences.newDealNotification.update(enabled)
  }
}
