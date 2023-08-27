package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import me.sujanpoudel.playdeals.common.ui.theme.LocalAppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.theme.asUITheme
import me.sujanpoudel.playdeals.common.utils.rememberSystemUiController

@Composable
actual fun ConfigureThemeForSystemUI() {
  val systemUiController = rememberSystemUiController()

  val themeMode by LocalAppearanceModeManager.current.appearanceMode.collectAsState()

  val uiTheme = themeMode.asUITheme()

  LaunchedEffect(uiTheme) {
    systemUiController.setSystemBarsColor(Color.Transparent, !uiTheme.isDark)
  }
}
