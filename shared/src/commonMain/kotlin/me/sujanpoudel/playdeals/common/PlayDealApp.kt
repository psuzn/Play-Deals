package me.sujanpoudel.playdeals.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.sujanpoudel.playdeals.common.navigation.NavGraph
import me.sujanpoudel.playdeals.common.navigation.NavHost
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.ui.screens.home.AppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.screens.home.LocalAppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.screens.newDeal.NewDealScreen
import me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher.ThemeSwitcherScreen
import me.sujanpoudel.playdeals.common.ui.theme.AppTheme
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.theme.LocalAppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.withWindowInsetPaddings

enum class Screens() {
  Home,
  NEW_DEAL,
  THEME_SCREEN,
}

val navGraph =
  NavGraph {
    destination(Screens.Home) {
      HomeScreen()
    }

    destination(Screens.NEW_DEAL) {
      NewDealScreen()
    }

    destination(Screens.THEME_SCREEN) {
      ThemeSwitcherScreen()
    }

    homePath = Screens.Home
  }

@Composable
fun PlayDealsApp(
  appearanceModeManager: AppearanceModeManager,
  appDealActionHandler: AppDealActionHandler = AppDealActionHandler { },
) {
  CompositionLocalProvider(
    LocalAppearanceModeManager provides appearanceModeManager,
    LocalAppDealActionHandler provides appDealActionHandler,
  ) {
    AppTheme {
      Box(
        modifier = Modifier
          .background(MaterialTheme.colors.background)
          .withWindowInsetPaddings(),
      ) {
        val navigator = remember { Navigator(navGraph) }

        NavHost(navigator)
      }
    }
  }
}
