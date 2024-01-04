package me.sujanpoudel.playdeals.common.domain.repositories

import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until
import kotlinx.serialization.json.Json
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase
import me.sujanpoudel.playdeals.common.domain.entities.ForexRateEntity
import me.sujanpoudel.playdeals.common.domain.models.Result
import me.sujanpoudel.playdeals.common.domain.models.api.ForexModel
import me.sujanpoudel.playdeals.common.domain.models.api.toEntity
import me.sujanpoudel.playdeals.common.domain.models.map
import me.sujanpoudel.playdeals.common.domain.models.onSuccess
import me.sujanpoudel.playdeals.common.domain.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.persistent.db.toEntity
import me.sujanpoudel.playdeals.common.domain.persistent.db.upsertAll
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

fun defaultUsdConversion() = ForexRateEntity(
  currency = "USD",
  symbol = "$",
  name = "United State Dollar",
  rate = 1f,
)

class ForexRepository(
  private val remoteAPI: RemoteAPI,
  private val appPreferences: AppPreferences,
  private val database: SqliteDatabase,
) {

  suspend fun refreshRatesIfNecessary(): Result<Unit> = withContext(Dispatchers.IO) {
    val shouldRefresh = appPreferences.forexRateAt.value?.let {
      val timeElapsed = it.until(Clock.System.now(), DateTimeUnit.SECOND)
      timeElapsed > Constants.FOREX_REFRESH_DURATION.inWholeSeconds
    } ?: let {
      loadBundledForex()
      true
    }

    if (shouldRefresh) {
      remoteAPI.getForexRates()
        .onSuccess { forex ->
          appPreferences.forexRateAt.update(forex.timestamp)
          database.forexQueries.upsertAll(forex.rates.map { it.toEntity() })
        }
        .map { }
    } else {
      Result.success(Unit)
    }
  }

  @OptIn(ExperimentalResourceApi::class)
  private suspend fun loadBundledForex() {
    runCatching {
      val forex = Json.decodeFromString<ForexModel>(resource(Constants.BUNDLED_FOREX).readBytes().decodeToString())
      appPreferences.forexRateAt.update(forex.timestamp)
      database.forexQueries.upsertAll(forex.rates.map { it.toEntity() })
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  fun forexRatesFlow(): Flow<List<ForexRateEntity>> = database.forexQueries.getAll()
    .asFlow()
    .mapLatest { it.executeAsList() }
    .map { forexRates -> forexRates.map { it.toEntity() } }

  fun forexUpdateAtFlow(): StateFlow<Instant?> = appPreferences.forexRateAt

  fun setPreferredConversion(forexRate: ForexRateEntity) =
    appPreferences.preferredCurrency.update(forexRate.currency)

  fun preferredConversionRateFlow(): Flow<ForexRateEntity> = forexRatesFlow()
    .combine(appPreferences.preferredCurrency) { rates, currency ->
      rates.find { it.currency == currency } ?: defaultUsdConversion()
    }
}
