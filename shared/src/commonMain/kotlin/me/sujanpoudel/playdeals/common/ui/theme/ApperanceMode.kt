package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.runtime.compositionLocalOf

enum class AppearanceMode {
  SYSTEM,
  DARK,
  LIGHT,
  BLACK,
}

enum class UIAppearanceMode {
  DARK,
  LIGHT,
  BLACK,
  ;

  val isDark
    get() = this == DARK || this == BLACK
}

val LocalAppearanceMode = compositionLocalOf<AppearanceMode> {
  throw Error("AppearanceMode Not Found")
}
