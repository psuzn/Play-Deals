package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

actual fun rubicFontFamily(): FontFamily = FontFamily(
  Font("font/rubic_bold.ttf", FontWeight.Bold),
  Font("font/rubic_light.ttf", FontWeight.Light),
  Font("font/rubic_medium.ttf", FontWeight.Medium),
  Font("font/rubic_regular.ttf", FontWeight.Normal),
)

actual fun robotoFontFamily(): FontFamily = FontFamily(
  Font("font/roboto_bold.ttf", FontWeight.Bold),
  Font("font/roboto_light.ttf", FontWeight.Light),
  Font("font/roboto_medium.ttf", FontWeight.Medium),
  Font("font/roboto_regular.ttf", FontWeight.Normal),
)
