package me.sujanpoudel.playdeals.common.domain.models.api

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.sujanpoudel.playdeals.common.domain.entities.ForexRateEntity

@Serializable
@Immutable
data class ForexModel(
  val timestamp: Instant,
  val rates: List<ForexRate>,
)

@Serializable
@Immutable
data class ForexRate(
  val currency: String,
  val symbol: String,
  val name: String,
  val rate: Float,
)

fun ForexRate.toEntity() = ForexRateEntity(
  currency = currency,
  symbol = symbol,
  name = name,
  rate = rate,
)
