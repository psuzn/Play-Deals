package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.runtime.compositionLocalOf
import me.sujanpoudel.playdeals.common.domain.models.AppDeal

fun interface AppDealActionHandler {
  fun handle(appDeal: AppDeal)
}

val LocalAppDealActionHandler = compositionLocalOf<AppDealActionHandler> {
  error("No AppDealActionHandler")
}
