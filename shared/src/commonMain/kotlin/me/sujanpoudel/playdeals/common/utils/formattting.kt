package me.sujanpoudel.playdeals.common.utils

import me.sujanpoudel.playdeals.common.Strings
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Float.formatAsPrice(): String {
  val int = toInt()
  val decimal = ((this - int) * 100).roundToInt()

  val formattedDecimal = if (decimal < 10) "${decimal}0"
  else "$decimal"

  return "${int}.${formattedDecimal}"
}

fun String.asCurrencySymbol() = when (this) {
  "USD" -> "$"
  else -> this
}

fun Duration.shallowFormatted(): String {
  if (inWholeDays.absoluteValue > 0) {
    return "${inWholeDays.absoluteValue} ${Strings.HomeScreen.DAYS}"
  }

  if (inWholeHours.absoluteValue > 0) {
    return "${inWholeHours.absoluteValue} ${Strings.HomeScreen.HOURS}"
  }

  if (inWholeMinutes.absoluteValue > 0) {
    return "${inWholeMinutes.absoluteValue} ${Strings.HomeScreen.MINUTES}"
  }

  return Strings.HomeScreen.A_MOMENT
}
