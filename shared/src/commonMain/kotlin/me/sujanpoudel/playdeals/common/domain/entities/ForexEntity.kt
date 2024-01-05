package me.sujanpoudel.playdeals.common.domain.entities

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Instant

@Immutable
data class Forex(
  val timestamp: Instant,
  val rates: List<ForexRateEntity>,
)

@Immutable
data class ForexRateEntity(
  val currency: String,
  val symbol: String,
  val name: String,
  val rate: Float,
)
