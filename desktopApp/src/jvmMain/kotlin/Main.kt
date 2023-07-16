import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import me.sujanpoudel.playdeals.common.MainDesktopView
import me.sujanpoudel.playdeals.common.ui.AppearanceManager
import me.sujanpoudel.playdeals.common.ui.asUITheme


fun main() = singleWindowApplication(
    title = "Chat",
    state = WindowState(
        size = DpSize(500.dp, 800.dp),
    ),
) {


    val appearanceManager = remember { AppearanceManager() }

    val appearanceMode by appearanceManager.appearanceMode.collectAsState()

    val uiAppearance = appearanceMode.asUITheme()

    LaunchedEffect(uiAppearance) {
        window.rootPane.putClientProperty(
            "apple.awt.windowAppearance",
            if (uiAppearance.isDark) "NSAppearanceNameVibrantDark"
            else "NSAppearanceNameVibrantLight"
        )
    }

    MainDesktopView(appearanceManager)
}
