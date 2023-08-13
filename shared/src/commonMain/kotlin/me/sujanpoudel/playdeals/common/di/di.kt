package me.sujanpoudel.playdeals.common.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import me.sujanpoudel.playdeals.common.di.conf.ConfigurableDI
import me.sujanpoudel.playdeals.common.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.newDeal.NewDealScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher.ThemeSwitcherViewModel
import me.sujanpoudel.playdeals.common.utils.isDebugBuild
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance


private val mainModule =
  DI.Module("mainModule") {

    bindProvider {
      HomeScreenViewModel(
        remoteAPI = instance(),
      )
    }

    bindProvider { NewDealScreenViewModel() }
    bindProvider { ThemeSwitcherViewModel() }

    bindSingleton {
      HttpClient {
        install(ContentNegotiation) { json() }
        Logging {
          level = if (isDebugBuild) LogLevel.ALL else LogLevel.NONE
          logger = object : Logger {
            override fun log(message: String) {
              println(message)
            }
          }
        }
        expectSuccess = true
      }
    }

    bindSingleton {
      RemoteAPI(instance())
    }
  }

val PrimaryDI =
  ConfigurableDI(true).apply {
    addImport(mainModule, true)
  }
