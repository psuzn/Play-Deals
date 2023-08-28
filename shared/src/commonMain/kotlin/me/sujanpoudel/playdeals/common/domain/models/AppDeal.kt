package me.sujanpoudel.playdeals.common.domain.models

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.utils.asCurrencySymbol
import me.sujanpoudel.playdeals.common.utils.formatAsPrice
import me.sujanpoudel.playdeals.common.utils.shallowFormatted

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
    get() = rating.formatAsPrice()

  fun formattedNormalPrice() = "${currency.asCurrencySymbol()}${normalPrice.formatAsPrice()}"

  @Composable
  fun formattedCurrentPrice() =
    if (currentPrice == 0f) {
      Strings.free
    } else {
      "${currency.asCurrencySymbol()}${currentPrice.formatAsPrice()}"
    }

  @Composable
  fun formattedExpiryInfo(): String {
    val now = Clock.System.now()

    val duration = now - offerExpiresIn

    return if (now > offerExpiresIn) {
      "${Strings.expired} ${duration.shallowFormatted()} ${Strings.ago}"
    } else {
      "${Strings.expiresIn} ${duration.shallowFormatted()}"
    }
  }
}
