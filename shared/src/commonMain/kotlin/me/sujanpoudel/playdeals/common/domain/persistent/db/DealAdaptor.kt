package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import migrations.Deal

private object ImagesAdaptor : ColumnAdapter<List<String>, String> {
  override fun decode(databaseValue: String) = Json.decodeFromString<List<String>>(databaseValue)
  override fun encode(value: List<String>) = Json.encodeToString(value)
}

private object InstantAdaptor : ColumnAdapter<Instant, String> {
  override fun decode(databaseValue: String) = Instant.parse(databaseValue)

  override fun encode(value: Instant) = value.toString()
}

fun buildDealAdaptor() = Deal.Adapter(
  imagesAdapter = ImagesAdaptor,
  offer_expires_inAdapter = InstantAdaptor,
  created_atAdapter = InstantAdaptor,
  updated_atAdapter = InstantAdaptor,
)
