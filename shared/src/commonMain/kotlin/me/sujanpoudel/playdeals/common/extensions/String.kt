package me.sujanpoudel.playdeals.common.extensions

@ExperimentalStdlibApi
fun String.capitalizeWords(): String =
  split(" ").joinToString(" ") { s ->
    s.replaceFirstChar {
      if (it.isLowerCase()) {
        it.titlecase()
      } else {
        it.toString()
      }
    }
  }
