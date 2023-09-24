package me.sujanpoudel.playdeals.common.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import me.sujanpoudel.playdeals.common.domain.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.utils.isDebugBuild
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal val networkingModule = DI.Module("networking") {

  bindSingleton {
    HttpClient {
      install(ContentNegotiation) { json() }
      Logging {
        level = if (isDebugBuild) LogLevel.ALL else LogLevel.NONE
      }
      expectSuccess = true
    }
  }

  bindSingleton { RemoteAPI(instance()) }
}
