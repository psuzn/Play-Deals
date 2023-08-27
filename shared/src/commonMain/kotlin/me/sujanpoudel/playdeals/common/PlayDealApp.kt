package me.sujanpoudel.playdeals.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import me.sujanpoudel.playdeals.common.navigation.NavGraph
import me.sujanpoudel.playdeals.common.navigation.NavHost
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.ui.ConfigureThemeForSystemUI
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.screens.home.LinkOpener
import me.sujanpoudel.playdeals.common.ui.screens.home.LocalLinkOpener
import me.sujanpoudel.playdeals.common.ui.screens.newDeal.NewDealScreen
import me.sujanpoudel.playdeals.common.ui.screens.settings.ThemeSwitcherScreen
import me.sujanpoudel.playdeals.common.ui.theme.AppTheme
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.theme.LocalAppearanceModeManager

enum class Screens() {
  Home,
  NEW_DEAL,
  SETTINGS,
}

val navGraph = NavGraph {
  destination(Screens.Home) {
    HomeScreen()
  }

  destination(Screens.NEW_DEAL) {
    NewDealScreen()
  }

  destination(Screens.SETTINGS) {
    ThemeSwitcherScreen()
  }

  homePath = Screens.Home
}

@Composable
fun PlayDealsApp(
  appearanceModeManager: AppearanceModeManager,
  linkOpener: LinkOpener = LinkOpener { },
) {
  CompositionLocalProvider(
    LocalAppearanceModeManager provides appearanceModeManager,
    LocalLinkOpener provides linkOpener,
  ) {
    AppTheme {
      ConfigureThemeForSystemUI()

      Box(
        modifier = Modifier
          .background(MaterialTheme.colorScheme.background)
          .windowInsetsPadding(
            WindowInsets.navigationBars
              .union(WindowInsets.statusBars)
              .union(WindowInsets.systemBars),
          ),
      ) {
        val navigator = remember { Navigator(navGraph) }

        NavHost(navigator)
      }
    }
  }
}
