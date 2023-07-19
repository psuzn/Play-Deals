package me.sujanpoudel.playdeals.common.extensions

import androidx.compose.ui.text.intl.Locale

@ExperimentalStdlibApi
fun String.capitalizeWords(): String = split(" ").joinToString(" ") { s ->
  s.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase()
    else it.toString()
  }
}
