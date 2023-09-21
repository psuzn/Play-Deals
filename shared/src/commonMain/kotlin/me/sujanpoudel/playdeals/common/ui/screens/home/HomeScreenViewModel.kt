package me.sujanpoudel.playdeals.common.ui.screens.home

import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.sujanpoudel.playdeals.common.AppPreferences
import me.sujanpoudel.playdeals.common.BuildKonfig
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.domain.entities.DealEntity
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.domain.models.Selectable
import me.sujanpoudel.playdeals.common.extensions.capitalizeWords
import me.sujanpoudel.playdeals.common.networking.RemoteAPI
import me.sujanpoudel.playdeals.common.networking.Result
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.viewModelScope

@OptIn(ExperimentalStdlibApi::class)
class HomeScreenViewModel(
  private val remoteAPI: RemoteAPI,
  private val appPreferences: AppPreferences,
) : ViewModel() {

  private val _state = MutableStateFlow(
    HomeScreenState(lastUpdatedTime = appPreferences.lastUpdatedTime.value),
  )
  val state = _state as StateFlow<HomeScreenState>

  init {
    getDeals()
    checkIfChangelogNeedsToBeShown()
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

  fun refreshDeals() = getDeals()

  private fun getDeals() {
    _state.update { state ->
      state.copy(
        isLoading = state.allDeals.isEmpty(),
        isRefreshing = state.allDeals.isNotEmpty(),
        persistentError = null,
      )
    }

    viewModelScope.launch {
      val result = remoteAPI.getDeals()
      _state.update { state ->
        when (result) {
          is Result.Error ->
            state.copy(
              isLoading = false,
              isRefreshing = false,
              persistentError = if (state.allDeals.isEmpty()) result.failure.message else null,
              errorOneOff = if (state.allDeals.isNotEmpty()) result.failure.message else null,
            )

          is Result.Success -> {
            appPreferences.lastUpdatedTime.update(Clock.System.now())
            state.copy(
              isLoading = false,
              isRefreshing = false,
              persistentError = null,
              errorOneOff = null,
              allDeals = result.data,
              filterOptions = buildFilterOption(result.data, state),
            )
          }
        }
      }
    }
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
