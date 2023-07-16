package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import me.sujanpoudel.playdeals.common.navigation.NavGraph
import me.sujanpoudel.playdeals.common.navigation.NavHost
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.ui.AppTheme
import me.sujanpoudel.playdeals.common.ui.AppearanceManager
import me.sujanpoudel.playdeals.common.ui.screens.first.FirstScreen
import me.sujanpoudel.playdeals.common.ui.screens.second.SecondScreen
import me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher.ThemeSwitcherScreen


enum class Screens() {
    SCREEN_A,
    SCREEN_B,
    THEME_SCREEN
}


val navGraph = NavGraph {
    destination(Screens.SCREEN_A) {
        FirstScreen()
    }

    destination(Screens.SCREEN_B) {
        SecondScreen()
    }

    destination(Screens.THEME_SCREEN) {
        ThemeSwitcherScreen()
    }

    homePath = Screens.SCREEN_A
}

@Composable
fun PlayDealsApp(appearanceManager: AppearanceManager) {

    val navigator = remember { Navigator(navGraph) }

    AppTheme(appearanceManager) {
        NavHost(navigator)
    }

}