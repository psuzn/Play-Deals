package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

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

private val BlackColorPalette = DarkColorPalette.copy(background = Color.Black)

val LocalAppearanceModeManager = compositionLocalOf<AppearanceModeManager> {
  throw Error("AppearanceManager Not Found")
}

@Composable
fun AppearanceMode.asUITheme() =
  when (this) {
    AppearanceMode.SYSTEM -> if (isSystemInDarkTheme()) UIAppearanceMode.DARK
    else UIAppearanceMode.LIGHT

    AppearanceMode.DARK -> UIAppearanceMode.DARK
    AppearanceMode.LIGHT -> UIAppearanceMode.LIGHT
    AppearanceMode.BLACK -> UIAppearanceMode.BLACK
  }

@Composable
fun AppTheme(
  content: @Composable () -> Unit,
) {
  val appearanceMode by LocalAppearanceModeManager.current.appearanceMode.collectAsState()

  val colorScheme = when (appearanceMode.asUITheme()) {
    UIAppearanceMode.DARK -> DarkColorPalette
    UIAppearanceMode.LIGHT -> LightColorPalette
    UIAppearanceMode.BLACK -> BlackColorPalette
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = defaultTypography(),
    content = content
  )
}
