package me.sujanpoudel.playdeals.common.strings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

enum class AppLanguage(val strings: AppStrings, val label: String, val flag: String) {
  EN(StringEn, "English", "🇬🇧"),
  NP(StringNp, "नेपाली", "🇳🇵"),
}

val Strings
  @Composable
  get(): AppStrings {
    val appLanguage = LocalAppLanguage.current
    return remember(appLanguage) { appLanguage.strings }
  }

val LocalAppLanguage = compositionLocalOf<AppLanguage> {
  throw Error("AppLanguage not defined")
}
