package me.sujanpoudel.playdeals.common.ui.screens.settings

import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.viewModel.ViewModel

data class SettingScreenState(
  val apperanceMode: AppearanceMode,
)

class SettingsScreenViewModel : ViewModel() {
  override fun onClose() {
    println("$this :: close")
  }
}
