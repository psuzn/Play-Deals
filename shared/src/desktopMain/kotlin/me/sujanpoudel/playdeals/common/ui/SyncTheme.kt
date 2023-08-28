package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode
import java.awt.Window
import javax.swing.JFrame

@Composable
actual fun ConfigureThemeForSystemUI(
  appearanceMode: AppearanceMode,
  uiAppearanceMode: UIAppearanceMode,
) {
  LaunchedEffect(Unit) {
    Window.getWindows().forEach { window ->
      (window as? JFrame)?.rootPane?.apply {
        putClientProperty("apple.awt.fullWindowContent", true)
        putClientProperty("apple.awt.transparentTitleBar", true)
        putClientProperty("apple.awt.windowTitleVisible", false)
        putClientProperty("apple.awt.fullscreenable", false)
      }
    }
  }
}
