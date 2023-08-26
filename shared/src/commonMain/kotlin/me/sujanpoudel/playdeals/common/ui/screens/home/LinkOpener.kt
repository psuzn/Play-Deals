package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.runtime.compositionLocalOf

fun interface LinkOpener {
  fun openLink(url: String)
}

val LocalLinkOpener = compositionLocalOf<LinkOpener> {
  error("No AppDealActionHandler")
}
