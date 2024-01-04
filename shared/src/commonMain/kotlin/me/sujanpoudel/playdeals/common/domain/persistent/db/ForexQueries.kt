package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.SuspendingTransactionWithoutReturn
import me.sujanpoudel.playdeals.common.ForexQueries
import me.sujanpoudel.playdeals.common.domain.entities.ForexRateEntity
import migrations.ForexRate

suspend fun ForexQueries.upsert(entity: ForexRateEntity) {
  with(entity) {
    upsert(
      name = name,
      currency = currency,
      symbol = symbol,
      rate = rate.toDouble(),
    )
  }
}

suspend fun ForexQueries.upsertAll(
  deals: List<ForexRateEntity>,
  runInTnx: suspend SuspendingTransactionWithoutReturn.() -> Unit = {},
) {
  transaction {
    deals.forEach {
      upsert(it)
    }
    runInTnx()
  }
}

fun ForexRate.toEntity() = ForexRateEntity(
  currency = currency,
  symbol = symbol,
  name = name,
  rate = rate.toFloat(),
)
