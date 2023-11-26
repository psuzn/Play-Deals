package me.sujanpoudel.playdeals.common.ui.screens.settings.notificationSettings

import kotlinx.coroutines.flow.StateFlow
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.viewModel.ViewModel

class NotificationSettingsScreenViewModel(
  private val appPreferences: AppPreferences,
) : ViewModel() {

  val freeDealNotification: StateFlow<Boolean> = appPreferences.subscribeToFreeDeals
  val nonFreeDealNotification: StateFlow<Boolean> = appPreferences.subscribeToDiscountDeals
  val summaryNotification: StateFlow<Boolean> = appPreferences.subscribeDealSummary
  val wasDeveloperModeEnabled = appPreferences.developerMode.value
  val developerMode: StateFlow<Boolean> = appPreferences.developerMode

  fun subscribeToAllDeals(enabled: Boolean) {
    subscribeToFreeDeals(enabled)
    subscribeDiscountDeals(enabled)
  }

  fun subscribeToFreeDeals(enabled: Boolean) = appPreferences.subscribeToFreeDeals.update(enabled)
  fun subscribeDiscountDeals(enabled: Boolean) = appPreferences.subscribeToDiscountDeals.update(enabled)
  fun subscribeToSummary(enabled: Boolean) = appPreferences.subscribeDealSummary.update(enabled)
  fun setDeveloperMode(enabled: Boolean) = appPreferences.developerMode.update(enabled)
}
