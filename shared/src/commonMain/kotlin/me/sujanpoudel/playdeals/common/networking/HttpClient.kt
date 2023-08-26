package me.sujanpoudel.playdeals.common.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import me.sujanpoudel.playdeals.common.Constants.API_BASE_URL
import me.sujanpoudel.playdeals.common.domain.models.Response

@OptIn(InternalAPI::class)
suspend inline fun <reified T> HttpClient.request(
  path: String,
  requestMethod: HttpMethod,
  requestBody: Any = EmptyContent,
  requestContentType: ContentType = ContentType.Any,
) = withContext<Result<T>>(Dispatchers.IO) {
  try {
    val response =
      request(path) {
        url("$API_BASE_URL$path")
        method = requestMethod
        body = requestBody
        contentType(requestContentType)
        build()
      }.body<Response<T>>()
    Result.success(response.data!!)
  } catch (e: Exception) {
    delay(1000) // rudimentary solution for flickering
    Result.failure(e.resolveToFailure())
  }
}

suspend inline fun <reified T> HttpClient.get(path: String) = request<T>(path, HttpMethod.Get)

suspend inline fun <reified T> HttpClient.post(
  path: String,
  requestBody: Any = EmptyContent,
) = request<T>(path, HttpMethod.Post, requestBody)
