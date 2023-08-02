package me.sujanpoudel.playdeals.common.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.domain.models.NewAppRequest

class RemoteAPI(
  private val client: HttpClient,
) {
  suspend fun getDeals(): Result<List<AppDeal>> = client.get<List<AppDeal>>("/deals")

  suspend fun addNewAppRequest(request: NewAppRequest) = client.post<Any>("/deals", request)
}
