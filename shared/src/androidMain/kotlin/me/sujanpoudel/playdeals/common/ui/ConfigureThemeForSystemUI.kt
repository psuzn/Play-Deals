package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode
import me.sujanpoudel.playdeals.common.utils.rememberSystemUiController

@Composable
actual fun ConfigureThemeForSystemUI(
  appearanceMode: AppearanceMode,
  uiAppearanceMode: UIAppearanceMode,
) {
  val systemUiController = rememberSystemUiController()

  LaunchedEffect(uiAppearanceMode) {
    systemUiController.setSystemBarsColor(Color.Transparent, !uiAppearanceMode.isDark)
  }
}
