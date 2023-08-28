package me.sujanpoudel.playdeals.common

import com.russhwolf.settings.ObservableSettings
import me.sujanpoudel.playdeals.common.strings.AppLanguage
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.utils.settings.boolSettingState
import me.sujanpoudel.playdeals.common.utils.settings.stringBackedSettingState

class AppPreferences(settings: ObservableSettings) {
  private object Keys {
    const val APPEARANCE_MODE = "APPEARANCE_MODE"
    const val DEVELOPER_MODE = "DEVELOPER_MODE_ENABLED"
    const val NEW_DEAL_NOTIFICATION = "NEW_DEAL_NOTIFICATION"
    const val PREFERRED_LANGUAGE = "PREFERRED_LANGUAGE"
    const val LAST_UPDATED_TIME = "LAST_UPDATED_TIME"
  }

  val appearanceMode = settings.stringBackedSettingState(
    Keys.APPEARANCE_MODE,
    AppearanceMode.SYSTEM,
    fromString = { AppearanceMode.valueOf(it) },
  )

  val appLanguage = settings.stringBackedSettingState(
    Keys.PREFERRED_LANGUAGE,
    AppLanguage.EN,
    fromString = { AppLanguage.valueOf(it) },
  )

  val developerMode = settings.boolSettingState(Keys.DEVELOPER_MODE, false)
  val newDealNotification = settings.boolSettingState(Keys.NEW_DEAL_NOTIFICATION, true)
}
