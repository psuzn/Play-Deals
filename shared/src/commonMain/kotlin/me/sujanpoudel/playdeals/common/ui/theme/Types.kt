package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.asFont
import me.sujanpoudel.playdeals.common.resources.MR

@Composable
fun rubicFontFamily(): FontFamily =
  FontFamily(
    MR.fonts.Rubic.bold.asFont(FontWeight.Bold, FontStyle.Normal)!!,
    MR.fonts.Rubic.light.asFont(FontWeight.Light, FontStyle.Normal)!!,
    MR.fonts.Rubic.medium.asFont(FontWeight.Medium, FontStyle.Normal)!!,
    MR.fonts.Rubic.regular.asFont(FontWeight.Normal, FontStyle.Normal)!!,
  )

@Composable
fun robotoFontFamily(): FontFamily =
  FontFamily(
    MR.fonts.Roboto.bold.asFont(FontWeight.Bold, FontStyle.Normal)!!,
    MR.fonts.Roboto.light.asFont(FontWeight.Light, FontStyle.Normal)!!,
    MR.fonts.Roboto.medium.asFont(FontWeight.Medium, FontStyle.Normal)!!,
    MR.fonts.Roboto.regular.asFont(FontWeight.Normal, FontStyle.Normal)!!,
  )

@Composable
fun defaultTypography() = robotoFontFamily().let { fontFamily ->
  Typography().let { typography ->
    typography.copy(
      displayLarge = typography.displayLarge.copy(fontFamily = fontFamily),
      displayMedium = typography.displayMedium.copy(fontFamily = fontFamily),
      displaySmall = typography.displaySmall.copy(fontFamily = fontFamily),
      headlineLarge = typography.headlineLarge.copy(fontFamily = fontFamily),
      headlineMedium = typography.headlineMedium.copy(fontFamily = fontFamily),
      headlineSmall = typography.headlineSmall.copy(fontFamily = fontFamily),
      titleLarge = typography.titleLarge.copy(fontFamily = fontFamily),
      titleMedium = typography.titleMedium.copy(fontFamily = fontFamily),
      titleSmall = typography.titleSmall.copy(fontFamily = fontFamily),
      bodyLarge = typography.bodyLarge.copy(fontFamily = fontFamily),
      bodyMedium = typography.bodyMedium.copy(fontFamily = fontFamily),
      bodySmall = typography.bodySmall.copy(fontFamily = fontFamily),
      labelLarge = typography.labelLarge.copy(fontFamily = fontFamily),
      labelMedium = typography.labelMedium.copy(fontFamily = fontFamily),
      labelSmall = typography.labelSmall.copy(fontFamily = fontFamily)
    )
  }
}
