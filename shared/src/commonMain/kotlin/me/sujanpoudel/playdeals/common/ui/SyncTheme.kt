package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode

@Composable
expect fun ConfigureThemeForSystemUI(appearanceMode: AppearanceMode, uiAppearanceMode: UIAppearanceMode)
