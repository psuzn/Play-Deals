package me.sujanpoudel.playdeals.common.domain.repositories

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import me.sujanpoudel.playdeals.common.SqliteDatabase
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity
import me.sujanpoudel.playdeals.common.domain.models.onSuccess
import me.sujanpoudel.playdeals.common.domain.models.skipData
import me.sujanpoudel.playdeals.common.domain.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.persistent.db.toEntity
import me.sujanpoudel.playdeals.common.domain.persistent.db.upsertAll

@OptIn(ExperimentalCoroutinesApi::class)
class DealsRepository(
  private val remoteAPI: RemoteAPI,
  private val database: SqliteDatabase,
  private val appPreferences: AppPreferences,
) {

  suspend fun dealsFlow(): Flow<List<DealEntity>> = withContext(Dispatchers.IO) {
    database.dealQueries.getAll()
      .asFlow()
      .mapLatest { it.executeAsList() }
      .map { deals ->
        deals.map { deal -> deal.toEntity() }
      }
  }

  suspend fun refreshDeals() = withContext(Dispatchers.IO) {
    remoteAPI.getDeals()
      .onSuccess { deals ->
        appPreferences.lastUpdatedTime.update(Clock.System.now())
        database.dealQueries.upsertAll(deals) {
          removeStaleDeals(deals)
        }
      }
      .skipData()
  }

  private suspend fun removeStaleDeals(upToDateDeals: List<DealEntity>) {
    val upToDateIds = upToDateDeals.map { it.id }
    database.dealQueries.removeStale(upToDateIds)
  }
}
