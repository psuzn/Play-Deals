package me.sujanpoudel.playdeals.common

import com.russhwolf.settings.ObservableSettings
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.utils.settings.boolSettingState
import me.sujanpoudel.playdeals.common.utils.settings.stringBackedSettingState

class AppPreferences(private val settings: ObservableSettings) {
  private object Keys {
    const val APPEARANCE_MODE = "APPEARANCE_MODE"
    const val DEVELOPER_MODE = "DEVELOPER_MODE_ENABLED"
    const val NEW_DEAL_NOTIFICATION = "NEW_DEAL_NOTIFICATION"
  }

  val appearanceMode = settings.stringBackedSettingState(
    Keys.APPEARANCE_MODE,
    AppearanceMode.SYSTEM,
    fromString = { AppearanceMode.valueOf(it) },
  )

  val developerMode = settings.boolSettingState(Keys.DEVELOPER_MODE, false)
  val newDealNotification = settings.boolSettingState(Keys.NEW_DEAL_NOTIFICATION, true)
}
