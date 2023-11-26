package me.sujanpoudel.playdeals.common.di

import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.newDeal.NewDealScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.settings.SettingsScreenViewModel
import me.sujanpoudel.playdeals.common.ui.screens.settings.notificationSettings.NotificationSettingsScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal val viewModelModule = DI.Module("viewModel") {

  bindProvider {
    HomeScreenViewModel(
      appPreferences = instance(),
      repository = instance(),
    )
  }

  bindProvider { NewDealScreenViewModel() }

  bindProvider { SettingsScreenViewModel(instance()) }

  bindProvider { NotificationSettingsScreenViewModel(instance()) }
}
