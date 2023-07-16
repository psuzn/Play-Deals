package me.sujanpoudel.playdeals.common


import androidx.compose.ui.window.ComposeUIViewController
import me.sujanpoudel.playdeals.common.ui.AppearanceManager

@Suppress("FunctionName", "unused")
fun MainViewController(appearanceManager: AppearanceManager) =
    ComposeUIViewController {
        PlayDealsApp(appearanceManager)
    }