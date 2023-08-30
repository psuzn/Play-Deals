package me.sujanpoudel.playdeals.common.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
suspend fun resourceFont(identifier: String, path: String, weight: FontWeight): Font {
  return Font(identifier, resource(path).readBytes(), weight)
}

actual fun rubicFontFamily(): FontFamily = runBlocking {
  FontFamily(
    resourceFont("rubic_bold", "font/rubic_bold.ttf", FontWeight.Bold),
    resourceFont("rubic_light", "font/rubic_light.ttf", FontWeight.Light),
    resourceFont("rubic_medium", "font/rubic_medium.ttf", FontWeight.Medium),
    resourceFont("rubic_regular", "font/rubic_regular.ttf", FontWeight.Normal),
  )
}

actual fun robotoFontFamily(): FontFamily = runBlocking {
  FontFamily(
    resourceFont("roboto_bold", "font/roboto_bold.ttf", FontWeight.Bold),
    resourceFont("roboto_light", "font/roboto_light.ttf", FontWeight.Light),
    resourceFont("roboto_medium", "font/roboto_medium.ttf", FontWeight.Medium),
    resourceFont("roboto_regular", "font/roboto_regular.ttf", FontWeight.Normal),
  )
}
