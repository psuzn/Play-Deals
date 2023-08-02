package me.sujanpoudel.playdeals.common

import androidx.compose.ui.window.ComposeUIViewController
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager

@Suppress("FunctionName", "unused")
fun MainViewController(appearanceModeManager: AppearanceModeManager) =
  ComposeUIViewController {
    PlayDealsApp(appearanceModeManager)
  }
