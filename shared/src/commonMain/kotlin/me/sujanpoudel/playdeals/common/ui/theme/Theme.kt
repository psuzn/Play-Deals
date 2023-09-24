package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.ui.ConfigureThemeForSystemUI

val blueishPurple = Color(0xFF7477CC)

val lightGray = Color(0xFF414141)

private val LightColorPalette = lightColorScheme(
  primary = blueishPurple,
  onPrimary = Color.White,

  secondary = blueishPurple,
  onSecondary = Color.White,

  surface = Color.White,
  onSurface = Color.Black,

  background = Color.White,
  onBackground = Color.Black,

  error = Color.Red,

  outlineVariant = Color.White,
)

private val DarkColorPalette = darkColorScheme(
  surface = lightGray,
  onSurface = Color.White,

  background = lightGray,
  onBackground = Color.White,

  primary = blueishPurple,
  onPrimary = Color.White,

  secondary = blueishPurple,
  onSecondary = Color.White,

  error = Color.Red,
)

private val BlackColorPalette = DarkColorPalette.copy(
  surface = Color(0xFF181818),

  background = Color.Black,
)

@Composable
fun AppearanceMode.asUITheme() =
  when (this) {
    AppearanceMode.SYSTEM -> if (isSystemInDarkTheme()) {
      UIAppearanceMode.DARK
    } else {
      UIAppearanceMode.LIGHT
    }

    AppearanceMode.DARK -> UIAppearanceMode.DARK
    AppearanceMode.LIGHT -> UIAppearanceMode.LIGHT
    AppearanceMode.BLACK -> UIAppearanceMode.BLACK
  }

@Composable
fun rememberColorScheme(uiAppearanceMode: UIAppearanceMode): ColorScheme {
  return remember(uiAppearanceMode) {
    when (uiAppearanceMode) {
      UIAppearanceMode.DARK -> DarkColorPalette
      UIAppearanceMode.LIGHT -> LightColorPalette
      UIAppearanceMode.BLACK -> BlackColorPalette
    }.let { colorScheme ->
      colorScheme.copy(
        outlineVariant = colorScheme.onBackground.copy(alpha = 0.1f),
      )
    }
  }
}

@Composable
fun AppTheme(preferences: AppPreferences, content: @Composable () -> Unit) {
  val appearanceMode by preferences.appearanceMode.collectAsState()

  val uiAppearanceMode = appearanceMode.asUITheme()

  val colorScheme = rememberColorScheme(appearanceMode.asUITheme())

  CompositionLocalProvider(LocalAppearanceMode provides appearanceMode) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = rememberTypography(),
    ) {
      ConfigureThemeForSystemUI(appearanceMode, uiAppearanceMode)

      content()
    }
  }
}
