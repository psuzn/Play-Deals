package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import me.sujanpoudel.playdeals.common.R

actual fun rubicFontFamily(): FontFamily = FontFamily(
  Font(R.font.rubic_bold, FontWeight.Bold),
  Font(R.font.rubic_light, FontWeight.Light),
  Font(R.font.rubic_medium, FontWeight.Medium),
  Font(R.font.rubic_regular, FontWeight.Normal),
)

actual fun robotoFontFamily(): FontFamily = FontFamily(
  Font(R.font.roboto_bold, FontWeight.Bold),
  Font(R.font.roboto_light, FontWeight.Light),
  Font(R.font.roboto_medium, FontWeight.Medium),
  Font(R.font.roboto_regular, FontWeight.Normal),
)
