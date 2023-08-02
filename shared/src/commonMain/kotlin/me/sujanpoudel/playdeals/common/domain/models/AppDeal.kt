package me.sujanpoudel.playdeals.common.domain.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

fun Float.format(): String {
  val int = toInt()
  val decimal = ((this - int) * 10).roundToInt()

  return "${toInt()}.$decimal"
}

val String.currencySymbol
  get() =
    when (this) {
      "USD" -> "$"
      else -> this
    }

@Serializable
data class AppDeal(
  val id: String,
  val name: String,
  val icon: String,
  val images: List<String>,
  val normalPrice: Float,
  val currentPrice: Float,
  val currency: String,
  val storeUrl: String,
  val category: String,
  val downloads: String,
  private val rating: Float,
  val offerExpiresIn: Instant,
  val createdAt: Instant,
  val updatedAt: Instant,
) {
  val ratingFormatted: String
    get() = rating.format()

  val formattedNormalPrice: String
    get() = "${currency.currencySymbol}${normalPrice.format()}"

  val formattedCurrentPrice: String
    get() =
      if (currentPrice == 0f) {
        "Free"
      } else {
        "${currency.currencySymbol}${currentPrice.format()}"
      }
}
