package me.sujanpoudel.playdeals.common.ui.screens.home

import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.BuildKonfig
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.domain.models.Result
import me.sujanpoudel.playdeals.common.domain.models.Selectable
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.domain.repositories.DealsRepository
import me.sujanpoudel.playdeals.common.domain.repositories.ForexRepository
import me.sujanpoudel.playdeals.common.extensions.capitalizeWords
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.viewModelScope

@OptIn(ExperimentalStdlibApi::class)
class HomeScreenViewModel(
  private val appPreferences: AppPreferences,
  private val dealsRepository: DealsRepository,
  private val forexRepository: ForexRepository,
) : ViewModel() {

  private val _state = MutableStateFlow(
    HomeScreenState(lastUpdatedTime = appPreferences.lastUpdatedTime.value),
  )

  val state = _state as StateFlow<HomeScreenState>

  init {
    observeDeals()
    refreshDeals()
    checkIfChangelogNeedsToBeShown()
    refreshForex()
  }

  private fun observeDeals() = viewModelScope.launch {
    dealsRepository.dealsFlow()
      .combine(forexRepository.preferredConversionRateFlow()) { deals, rate ->
        deals.map { deal ->
          deal.copy(
            currentPrice = deal.currentPrice * rate.rate,
            normalPrice = deal.normalPrice * rate.rate,
            currency = rate.symbol,
          )
        }
      }
      .collect { deals ->
        _state.update { state ->
          state.copy(
            persistentError = if (deals.isNotEmpty()) null else state.persistentError,
            errorOneOff = if (deals.isNotEmpty()) state.persistentError else null,
            allDeals = deals,
            filterOptions = buildFilterOption(deals, state),
            isRefreshing = state.isRefreshing || (deals.isNotEmpty() && state.isLoading),
            isLoading = deals.isEmpty() && state.isLoading,
          )
        }
      }
  }

  private fun checkIfChangelogNeedsToBeShown() {
    if (BuildKonfig.MAJOR_RELEASE && appPreferences.getChangelogShownVersion() != BuildKonfig.VERSION_CODE) {
      viewModelScope.launch {
        delay(1000)
        _state.update {
          it.copy(
            destinationOneOff = Screens.CHANGELOG,
          )
        }
      }
    }
  }

  fun refreshDeals() {
    _state.update { state ->
      state.copy(
        isLoading = state.allDeals.isEmpty(),
        isRefreshing = state.allDeals.isNotEmpty(),
        persistentError = null,
        errorOneOff = null,
      )
    }

    viewModelScope.launch {
      val result = dealsRepository.refreshDeals()
      _state.update { state ->
        when (result) {
          is Result.Error -> state.copy(
            isLoading = false,
            isRefreshing = false,
            persistentError = if (state.allDeals.isEmpty()) result.failure.message else null,
            errorOneOff = if (state.allDeals.isNotEmpty()) result.failure.message else null,
          )

          is Result.Success -> state.copy(
            isLoading = false,
            isRefreshing = false,
            persistentError = null,
            errorOneOff = null,
          )
        }
      }
    }
  }

  fun refreshForex() = viewModelScope.launch {
    forexRepository.refreshRatesIfNecessary()
  }

  fun clearErrorOneOff() {
    _state.update { state ->
      state.copy(errorOneOff = null)
    }
  }

  fun clearOneOffDestination() {
    _state.update { state ->
      state.copy(destinationOneOff = null)
    }
  }

  fun toggleFilterItem(item: DealFilterOption) {
    _state.update { state ->
      state.copy(
        filterOptions =
          state.filterOptions.map {
            if (it.data == item) {
              it.copy(selected = !it.selected)
            } else {
              it
            }
          },
      )
    }
  }

  private fun buildFilterOption(
    appDeals: List<DealEntity>,
    state: HomeScreenState,
  ): List<Selectable<DealFilterOption>> {
    val categoryFilters: List<Selectable<DealFilterOption>> =
      appDeals
        .map { it.category }
        .distinct()
        .map {
          DealFilterOption.Category(
            title = it.lowercase().capitalizeWords(),
            value = it,
          )
        }.map { newItem ->
          Selectable(
            data = newItem,
            selected = state.filterOptions.any { oldItem ->
              oldItem.data is DealFilterOption.Category &&
                newItem.value == oldItem.data.value &&
                oldItem.selected
            },
          )
        }

    val otherFilters = listOf(
      DealFilterOption.NewlyAddedApps,
      DealFilterOption.FreeApps,
    ).map { newItem ->
      Selectable(
        data = newItem,
        selected =
          state.filterOptions.any { oldItem ->
            oldItem.data.instanceOf(newItem::class) && oldItem.selected
          },
      )
    }

    return otherFilters + categoryFilters
  }
}
