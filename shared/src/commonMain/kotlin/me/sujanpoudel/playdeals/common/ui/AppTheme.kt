package me.sujanpoudel.playdeals.common.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

val blueishPurple = Color(0xFF7477CC)

val lightGray = Color(0xFF414141)

private val LightColorPalette = lightColors(
    primary = blueishPurple,
    primaryVariant = blueishPurple,
    secondary = blueishPurple,
    secondaryVariant = blueishPurple,

    surface = Color.White,
    background = Color.White,

    onBackground = Color.Black,
)

private val DarkColorPalette = darkColors(
    primary = blueishPurple,
    primaryVariant = blueishPurple,
    secondary = blueishPurple,
    secondaryVariant = blueishPurple,

    surface = lightGray,
    background = lightGray,

    onBackground = Color.White,
)

private val BlackColorPalette = darkColors(
    primary = blueishPurple,
    primaryVariant = blueishPurple,
    secondary = blueishPurple,
    secondaryVariant = blueishPurple,

    surface = lightGray,
    background = Color.Black,

    onBackground = Color.White,
)

val LocalAppearanceManager = compositionLocalOf<AppearanceManager> {
    throw Error("AppearanceManager Not Found")
}

@Composable
fun AppearanceMode.asUITheme() = when (this) {
    AppearanceMode.SYSTEM -> if (isSystemInDarkTheme())
        UIAppearanceMode.DARK else UIAppearanceMode.LIGHT

    AppearanceMode.DARK -> UIAppearanceMode.DARK
    AppearanceMode.LIGHT -> UIAppearanceMode.LIGHT
    AppearanceMode.BLACK -> UIAppearanceMode.BLACK
}

@Composable
fun AppTheme(
    appearanceManager: AppearanceManager,
    content: @Composable () -> Unit
) {
    val appearanceMode by appearanceManager.appearanceMode.collectAsState()

    val colors = when (appearanceMode.asUITheme()) {
        UIAppearanceMode.DARK -> DarkColorPalette
        UIAppearanceMode.LIGHT -> LightColorPalette
        UIAppearanceMode.BLACK -> BlackColorPalette
    }

    CompositionLocalProvider(LocalAppearanceManager provides appearanceManager) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}