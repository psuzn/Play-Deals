package me.sujanpoudel.playdeals.common.utils

import me.sujanpoudel.playdeals.common.strings.StringEn
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.time.Duration

fun Float.formatAsPrice(): String {
  val int = toInt()
  val decimal = ((this - int) * 100).roundToInt()

  val formattedDecimal = if (decimal < 10) {
    "${decimal}0"
  } else {
    "$decimal"
  }

  return "$int.$formattedDecimal"
}

fun Duration.shallowFormatted(): String {
  if (inWholeDays.absoluteValue > 0) {
    return "${inWholeDays.absoluteValue} ${StringEn.days}"
  }

  if (inWholeHours.absoluteValue > 0) {
    return "${inWholeHours.absoluteValue} ${StringEn.hours}"
  }

  if (inWholeMinutes.absoluteValue > 0) {
    return "${inWholeMinutes.absoluteValue} ${StringEn.minutes}"
  }

  return StringEn.aMoment
}
