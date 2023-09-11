package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.domain.models.Selectable

@Immutable
data class HomeScreenState(
  val allAppDeals: List<AppDeal> = emptyList(),
  val filterOptions: List<Selectable<DealFilterOption>> = emptyList(),
  val lastUpdatedTime: Instant = Clock.System.now(),
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val persistentError: String? = null,
  val errorOneOff: String? = null,
  val destinationOneOff: Screens? = null,
) {
  val dealsToDisplay: List<AppDeal>
    get() {
      val selectedCategories = filterOptions
        .filter { it.data is DealFilterOption.Category && it.selected }
        .map { it.data }

      val selectedOtherFilters = filterOptions
        .filter { it.data !is DealFilterOption.Category && it.selected }
        .map { it.data }

      return allAppDeals.filter { appDeal ->
        val inSelectedCategory = selectedCategories.isEmpty() ||
          selectedCategories.any {
            it is DealFilterOption.Category && it.value == appDeal.category
          }

        val matchesOtherFilter = selectedOtherFilters.isEmpty() ||
          selectedOtherFilters.all {
            when (it) {
              is DealFilterOption.Category -> throw Error("unreachable")
              DealFilterOption.FreeApps -> appDeal.currentPrice == 0f
              DealFilterOption.NewlyAddedApps -> appDeal.createdAt > lastUpdatedTime
            }
          }
        inSelectedCategory && matchesOtherFilter
      }
    }
}
