package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily

expect fun rubicFontFamily(): FontFamily

expect fun robotoFontFamily(): FontFamily

@Composable
fun rememberTypography() = remember(Unit) {
  val robotoFontFamily = robotoFontFamily()
  val rubicFontFamily = rubicFontFamily()

  Typography().let { typography ->
    Typography().copy(
      displayLarge = typography.displayLarge.copy(fontFamily = robotoFontFamily),
      displayMedium = typography.displayMedium.copy(fontFamily = robotoFontFamily),
      displaySmall = typography.displaySmall.copy(fontFamily = robotoFontFamily),

      headlineLarge = typography.headlineLarge.copy(fontFamily = robotoFontFamily),
      headlineMedium = typography.headlineMedium.copy(fontFamily = robotoFontFamily),
      headlineSmall = typography.headlineSmall.copy(fontFamily = robotoFontFamily),

      titleLarge = typography.titleLarge.copy(fontFamily = rubicFontFamily),
      titleMedium = typography.titleMedium.copy(fontFamily = rubicFontFamily),
      titleSmall = typography.titleSmall.copy(fontFamily = robotoFontFamily),

      bodyLarge = typography.bodyLarge.copy(fontFamily = robotoFontFamily),
      bodyMedium = typography.bodyMedium.copy(fontFamily = robotoFontFamily),
      bodySmall = typography.bodySmall.copy(fontFamily = robotoFontFamily),

      labelLarge = typography.labelLarge.copy(fontFamily = robotoFontFamily),
      labelMedium = typography.labelMedium.copy(fontFamily = robotoFontFamily),
      labelSmall = typography.labelSmall.copy(fontFamily = robotoFontFamily),
    )
  }
}
