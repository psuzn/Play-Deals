package me.sujanpoudel.playdeals.common.domain.persistent

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.set
import kotlinx.datetime.Instant
import me.sujanpoudel.playdeals.common.strings.AppLanguage
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.utils.settings.boolSettingState
import me.sujanpoudel.playdeals.common.utils.settings.nullableStringBackedSettingState
import me.sujanpoudel.playdeals.common.utils.settings.stringBackedSettingState
import me.sujanpoudel.playdeals.common.utils.settings.stringSettingState

class AppPreferences(private val settings: ObservableSettings) {
  private object Keys {
    const val APPEARANCE_MODE = "APPEARANCE_MODE"
    const val DEVELOPER_MODE = "DEVELOPER_MODE_ENABLED"

    const val NEW_DEAL_NOTIFICATION = "NEW_DEAL_NOTIFICATION"
    const val NEW_DISCOUNT_DEAL_NOTIFICATION = "NEW_DISCOUNT_DEAL_NOTIFICATION"
    const val SUMMARY_NOTIFICATION = "SUMMARY_NOTIFICATION"

    const val PREFERRED_LANGUAGE = "PREFERRED_LANGUAGE"
    const val LAST_UPDATED_TIME = "LAST_UPDATED_TIME"
    const val CHANGELOG_SHOWN_FOR_VERSION = "CHANGELOG_SHOWN_FOR_VERSION"
    const val FOREX_RATES_AT = "FOREX_RATE_AT"
    const val PREFERRED_CURRENCY = "PREFERRED_CURRENCY"
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

  val lastUpdatedTime = settings.stringBackedSettingState(
    Keys.LAST_UPDATED_TIME,
    Instant.fromEpochMilliseconds(0),
    fromString = { Instant.parse(it) },
  )

  val forexRateAt = settings.nullableStringBackedSettingState(
    Keys.FOREX_RATES_AT,
    toString = Instant::toString,
    fromString = Instant::parse,
  )

  val preferredCurrency = settings.stringSettingState(Keys.PREFERRED_CURRENCY, "USD")

  val developerMode = settings.boolSettingState(Keys.DEVELOPER_MODE, false)
  val subscribeToFreeDeals = settings.boolSettingState(Keys.NEW_DEAL_NOTIFICATION, true)
  val subscribeToDiscountDeals = settings.boolSettingState(Keys.NEW_DISCOUNT_DEAL_NOTIFICATION, true)
  val subscribeDealSummary = settings.boolSettingState(Keys.SUMMARY_NOTIFICATION, true)

  fun getChangelogShownVersion() = settings.getIntOrNull(Keys.CHANGELOG_SHOWN_FOR_VERSION)
  fun setChangelogShownVersion(version: Int) = settings.set(Keys.CHANGELOG_SHOWN_FOR_VERSION, version)
}
