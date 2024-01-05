package me.sujanpoudel.playdeals.common

import kotlin.time.Duration.Companion.hours

object Constants {
  const val API_BASE_URL = "https://api.play-deals.contabo.sujanpoudel.me/api"
  const val ABOUT_ME_URL = "https://sujanpoudel.me"
  val DATABASE_NAME = "${BuildKonfig.PACKAGE_NAME}.db"

  val FOREX_REFRESH_DURATION = 5.hours
  const val BUNDLED_FOREX = "raw/forex.json"
}
