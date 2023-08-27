package me.sujanpoudel.playdeals.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.awt.Window
import javax.swing.JFrame

@Composable
actual fun ConfigureThemeForSystemUI() {
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
