package me.sujanpoudel.playdeals.common.di

import com.russhwolf.settings.Settings
import me.sujanpoudel.playdeals.common.SqliteDatabase
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.persistent.db.buildDealAdaptor
import me.sujanpoudel.playdeals.common.domain.persistent.db.createSqlDriver
import me.sujanpoudel.playdeals.common.domain.repositories.DealsRepository
import me.sujanpoudel.playdeals.common.utils.settings.asObservableSettings
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

internal val persistentModule = DI.Module("persistent") {

  bindSingleton {
    Settings().asObservableSettings()
  }

  bindSingleton { AppPreferences(settings = instance()) }

  bindSingleton { createSqlDriver() }

  bindSingleton { SqliteDatabase(instance(), buildDealAdaptor()) }

  bindSingleton {
    DealsRepository(
      remoteAPI = instance(),
      database = instance(),
      appPreferences = instance(),
    )
  }
}
