package me.sujanpoudel.playdeals.common.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.LocalUIViewController
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode
import platform.UIKit.UIColor
import platform.UIKit.UIUserInterfaceStyle

private fun Color.toUIColor(): UIColor = UIColor(
  red = this.red.toDouble(),
  green = this.green.toDouble(),
  blue = this.blue.toDouble(),
  alpha = this.alpha.toDouble(),
)

@Composable
actual fun ConfigureThemeForSystemUI(
  appearanceMode: AppearanceMode,
  uiAppearanceMode: UIAppearanceMode,
) {
  val uiController = LocalUIViewController.current

  val backgroundColor = MaterialTheme.colorScheme.background

  LaunchedEffect(backgroundColor, uiAppearanceMode) {
    val window = uiController.view.window ?: return@LaunchedEffect

    window.rootViewController?.view?.backgroundColor = backgroundColor.toUIColor()

    window.overrideUserInterfaceStyle = when (appearanceMode) {
      AppearanceMode.SYSTEM -> UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified

      AppearanceMode.DARK, AppearanceMode.BLACK -> UIUserInterfaceStyle.UIUserInterfaceStyleDark

      AppearanceMode.LIGHT -> UIUserInterfaceStyle.UIUserInterfaceStyleLight
    }
  }
}
