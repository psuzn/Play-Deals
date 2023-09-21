package me.sujanpoudel.playdeals.common.networking

import io.ktor.client.HttpClient
import me.sujanpoudel.playdeals.common.domain.models.api.AppDealModel
import me.sujanpoudel.playdeals.common.domain.models.api.toEntity

class RemoteAPI(
  private val client: HttpClient,
) {
  suspend fun getDeals() = client.get<List<AppDealModel>>("/deals?take=500").map {
    it.map { deal ->
      deal.toEntity()
    }
  }
}
