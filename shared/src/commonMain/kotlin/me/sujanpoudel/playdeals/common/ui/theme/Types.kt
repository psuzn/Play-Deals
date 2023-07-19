package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.asFont
import me.sujanpoudel.playdeals.common.resources.MR

@Composable
fun rubicFontFamily(): FontFamily = FontFamily(
  MR.fonts.Rubic.bold.asFont(FontWeight.Bold, FontStyle.Normal)!!,
  MR.fonts.Rubic.light.asFont(FontWeight.Light, FontStyle.Normal)!!,
  MR.fonts.Rubic.medium.asFont(FontWeight.Medium, FontStyle.Normal)!!,
  MR.fonts.Rubic.regular.asFont(FontWeight.Normal, FontStyle.Normal)!!,
)


@Composable
fun robotoFontFamily(): FontFamily = FontFamily(
  MR.fonts.Roboto.bold.asFont(FontWeight.Bold, FontStyle.Normal)!!,
  MR.fonts.Roboto.light.asFont(FontWeight.Light, FontStyle.Normal)!!,
  MR.fonts.Roboto.medium.asFont(FontWeight.Medium, FontStyle.Normal)!!,
  MR.fonts.Roboto.regular.asFont(FontWeight.Normal, FontStyle.Normal)!!,
)


@Composable
fun defaultTypography(): Typography = robotoFontFamily().let { fontFamily ->
  Typography(
    defaultFontFamily = fontFamily,
    body1 = TextStyle(
      fontFamily = fontFamily,
      fontWeight = FontWeight.Normal,
      fontSize = 14.sp
    ),
    body2 = TextStyle(
      fontFamily = fontFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp
    ),
    h1 = TextStyle(
      fontFamily = fontFamily,
      fontWeight = FontWeight.SemiBold,
      fontSize = 26.sp,
    ),
    h2 = TextStyle(
      fontFamily = fontFamily,
      fontSize = 23.sp,
      fontWeight = FontWeight.Bold
    ),
    h3 = TextStyle(
      fontFamily = fontFamily,
      fontSize = 16.sp,
      fontWeight = FontWeight.SemiBold
    ),
    button = TextStyle(
      fontFamily = fontFamily,
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
    ),
  )
}
