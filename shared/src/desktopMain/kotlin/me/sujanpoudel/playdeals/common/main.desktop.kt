package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import me.sujanpoudel.playdeals.common.ui.screens.home.AppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import java.awt.Desktop
import java.net.URI

@Composable
fun MainDesktopView(
  appearanceModeManager: AppearanceModeManager,
) {
  val appDealActionHandler = remember {
    AppDealActionHandler {
      Desktop.getDesktop().browse(URI.create(it.storeUrl))
    }
  }

  PlayDealsApp(appearanceModeManager, appDealActionHandler)
}
