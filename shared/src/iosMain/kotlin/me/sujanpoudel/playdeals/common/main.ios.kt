package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import me.sujanpoudel.playdeals.common.ui.screens.home.AppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication

@Suppress("FunctionName", "unused")
fun MainViewController(appearanceModeManager: AppearanceModeManager) = ComposeUIViewController {

  val appDealActionHandler = remember {
    AppDealActionHandler {
      UIApplication.sharedApplication.openURL(URLWithString(it.storeUrl)!!)
    }
  }

  PlayDealsApp(
    appearanceModeManager,
    appDealActionHandler
  )
}
