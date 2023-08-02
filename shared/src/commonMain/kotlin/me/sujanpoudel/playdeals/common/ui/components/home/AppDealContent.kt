package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.domain.models.Selectable
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenState
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object AppDealContent {
  @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
  @Composable
  operator fun invoke(
    state: HomeScreenState,
    onRequestRefresh: () -> Unit,
    onToggleFilterOption: (DealFilterOption) -> Unit,
  ) {
    val pullToRefreshState = rememberPullRefreshState(state.isRefreshing, onRequestRefresh)

    Box(
      modifier =
        Modifier.fillMaxSize()
          .pullRefresh(pullToRefreshState),
    ) {
      Column {
        if (state.filterOptions.isNotEmpty()) {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            itemsIndexed(state.filterOptions) { index, it ->
              FilterChipItem(
                index = index,
                filterOption = it,
                onToggleFilterOption = onToggleFilterOption,
              )
            }
          }
        }

        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(32.dp),
          contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
          modifier = Modifier.fillMaxSize(),
        ) {
          items(state.dealsToDisplay, key = { it.id }) {
            AppDealItem(
              appDeal = it,
              modifier = Modifier.animateItemPlacement(),
            )
          }
        }
      }

      PullRefreshIndicator(
        state.isRefreshing,
        pullToRefreshState,
        Modifier.align(Alignment.TopCenter),
        scale = true,
        contentColor = MaterialTheme.colors.primary,
      )
    }
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  fun FilterChipItem(
    index: Int,
    filterOption: Selectable<DealFilterOption>,
    onToggleFilterOption: (DealFilterOption) -> Unit,
  ) {
    FilterChip(
      onClick = { onToggleFilterOption(filterOption.data) },
      selected = filterOption.selected,
      colors =
        ChipDefaults.filterChipColors(
          selectedBackgroundColor = MaterialTheme.colors.primary,
        ),
    ) {
      Text(
        text = filterOption.data.label,
        color =
          if (filterOption.selected) {
            MaterialTheme.colors.onPrimary
          } else {
            MaterialTheme.colors.onSurface
          },
        style = MaterialTheme.typography.body1,
      )
    }

    if (index == 1) {
      Box(
        modifier =
          Modifier
            .padding(start = 8.dp)
            .width(1.dp)
            .height(22.dp)
            .background(MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA)),
      )
    }
  }
}
