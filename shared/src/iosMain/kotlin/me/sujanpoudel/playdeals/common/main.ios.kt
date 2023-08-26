package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import me.sujanpoudel.playdeals.common.ui.screens.home.LinkOpener
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import platform.Foundation.NSURL.Companion.URLWithString
import platform.UIKit.UIApplication

@Suppress("FunctionName", "unused")
fun MainViewController(appearanceModeManager: AppearanceModeManager) = ComposeUIViewController {
  val linkOpener = remember {
    LinkOpener {
      UIApplication.sharedApplication.openURL(URLWithString(it)!!)
    }
  }

  PlayDealsApp(appearanceModeManager, linkOpener)
}
