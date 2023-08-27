package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import me.sujanpoudel.playdeals.common.AppPreferences
import me.sujanpoudel.playdeals.common.di.PrimaryDI
import me.sujanpoudel.playdeals.common.ui.theme.asUITheme
import me.sujanpoudel.playdeals.common.utils.rememberSystemUiController
import org.kodein.di.direct
import org.kodein.di.instance

@Composable
actual fun ConfigureThemeForSystemUI() {
  val systemUiController = rememberSystemUiController()

  val appPreferences = remember { PrimaryDI.direct.instance<AppPreferences>() }

  val themeMode by appPreferences.appearanceMode.collectAsState()

  val uiTheme = themeMode.asUITheme()

  LaunchedEffect(uiTheme) {
    systemUiController.setSystemBarsColor(Color.Transparent, !uiTheme.isDark)
  }
}
