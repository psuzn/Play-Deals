package me.sujanpoudel.playdeals.common.domain.networking

import io.ktor.client.HttpClient
import me.sujanpoudel.playdeals.common.domain.models.api.AppDealModel
import me.sujanpoudel.playdeals.common.domain.models.api.ForexModel
import me.sujanpoudel.playdeals.common.domain.models.api.toEntity
import me.sujanpoudel.playdeals.common.domain.models.map

class RemoteAPI(
  private val client: HttpClient,
) {
  suspend fun getDeals() = client.get<List<AppDealModel>>("/deals?take=500").map {
    it.map { deal ->
      deal.toEntity()
    }
  }

  suspend fun getForexRates() = client.get<ForexModel>("/forex")
}
