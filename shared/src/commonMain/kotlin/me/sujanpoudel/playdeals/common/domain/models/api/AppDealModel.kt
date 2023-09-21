package me.sujanpoudel.playdeals.common.domain.models.api

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity

@Serializable
class AppDealModel(
  val id: String,
  val name: String,
  val icon: String,
  val images: List<String>,
  val normalPrice: Float,
  val currentPrice: Float,
  val currency: String,
  val url: String,
  val category: String,
  val downloads: String,
  val rating: Float,
  val offerExpiresIn: Instant,
  val type: String,
  val source: String,
  val createdAt: Instant,
  val updatedAt: Instant,
)

fun AppDealModel.toEntity() = DealEntity(
  id = id,
  name = name,
  icon = icon,
  images = images,
  normalPrice = normalPrice,
  currentPrice = currentPrice,
  currency = currency,
  url = url,
  category = category,
  downloads = downloads,
  rating = rating,
  offerExpiresIn = offerExpiresIn,
  type = type,
  source = source,
  createdAt = createdAt,
  updatedAt = updatedAt,
)
