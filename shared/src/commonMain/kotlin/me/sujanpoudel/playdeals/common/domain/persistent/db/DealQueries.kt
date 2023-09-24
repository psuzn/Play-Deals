package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.SuspendingTransactionWithoutReturn
import me.sujanpoudel.playdeals.common.DealQueries
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity
import migrations.Deal

suspend fun DealQueries.upsert(dealEntity: DealEntity) {
  with(dealEntity) {
    upsert(
      id = id,
      name = name,
      icon = icon,
      images = images,
      normal_price = normalPrice.toDouble(),
      current_price = currentPrice.toDouble(),
      currency = currency,
      url = url,
      category = category,
      downloads = downloads,
      rating = rating.toDouble(),
      type = type,
      source = source,
      offer_expires_in = offerExpiresIn,
      created_at = createdAt,
      updated_at = updatedAt,
    )
  }
}

suspend fun DealQueries.upsertAll(
  deals: List<DealEntity>,
  runInTnx: suspend SuspendingTransactionWithoutReturn.() -> Unit = {},
) {
  transaction {
    deals.forEach {
      upsert(it)
    }
    runInTnx()
  }
}

fun Deal.toEntity() = DealEntity(
  id = id,
  name = name,
  icon = icon,
  images = images,
  normalPrice = normal_price.toFloat(),
  currentPrice = current_price.toFloat(),
  currency = currency,
  url = url,
  category = category,
  downloads = downloads,
  rating = rating.toFloat(),
  offerExpiresIn = offer_expires_in,
  type = type,
  source = source,
  createdAt = created_at,
  updatedAt = updated_at,
)
