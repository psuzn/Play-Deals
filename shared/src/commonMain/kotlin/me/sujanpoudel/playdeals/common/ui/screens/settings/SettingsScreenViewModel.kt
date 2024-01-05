package me.sujanpoudel.playdeals.common.ui.screens.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import me.sujanpoudel.playdeals.common.domain.entities.ForexRateEntity
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.repositories.ForexRepository
import me.sujanpoudel.playdeals.common.domain.repositories.defaultUsdConversion
import me.sujanpoudel.playdeals.common.strings.AppLanguage
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.viewModelScope

class SettingsScreenViewModel(
  private val appPreferences: AppPreferences,
  private val forexRepository: ForexRepository,
) : ViewModel() {

  val appearanceMode: StateFlow<AppearanceMode> = appPreferences.appearanceMode
  val developerModeEnabled: StateFlow<Boolean> = appPreferences.developerMode
  val appLanguage: StateFlow<AppLanguage> = appPreferences.appLanguage
  val forexUpdatedAt: StateFlow<Instant?> = forexRepository.forexUpdateAtFlow()
  val forexRates: StateFlow<List<ForexRateEntity>> = forexRepository.forexRatesFlow()
    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  val preferredConversion = MutableStateFlow(defaultUsdConversion())

  init {
    viewModelScope.launch {
      forexRepository.preferredConversionRateFlow()
        .collectLatest { preferredConversion.value = it }
    }
  }

  fun setAppearanceMode(mode: AppearanceMode) {
    appPreferences.appearanceMode.update(mode)
  }

  fun setDeveloperModeEnabled(enabled: Boolean) {
    appPreferences.developerMode.update(enabled)
  }

  fun setAppLanguage(appLanguage: AppLanguage) {
    appPreferences.appLanguage.update(appLanguage)
  }

  fun setPreferredCurrency(rate: ForexRateEntity) {
    forexRepository.setPreferredConversion(rate)
  }
}
