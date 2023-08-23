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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.domain.models.Selectable
import me.sujanpoudel.playdeals.common.ui.screens.home.HomeScreenState
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object AppDealContent {
  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  operator fun invoke(
    homeScreenState: HomeScreenState,
    onToggleFilterOption: (DealFilterOption) -> Unit,
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      if (homeScreenState.filterOptions.isNotEmpty()) {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          itemsIndexed(homeScreenState.filterOptions) { index, it ->
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
        items(homeScreenState.dealsToDisplay, key = { it.id }) {
          AppDealItem(
            appDeal = it,
            modifier = Modifier.animateItemPlacement(),
          )
        }
      }
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun FilterChipItem(
    index: Int,
    filterOption: Selectable<DealFilterOption>,
    onToggleFilterOption: (DealFilterOption) -> Unit,
  ) {
    FilterChip(
      onClick = { onToggleFilterOption(filterOption.data) },
      selected = filterOption.selected,
      elevation = null,
      colors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = MaterialTheme.colorScheme.primary,
      ),
      label = {
        Text(
          text = filterOption.data.label,
          color = if (filterOption.selected) {
            MaterialTheme.colorScheme.onPrimary
          } else {
            MaterialTheme.colorScheme.onSurface
          },
          style = MaterialTheme.typography.labelMedium,
        )
      },
    )

    if (index == 1) {
      Box(
        modifier = Modifier
          .padding(start = 8.dp)
          .width(1.dp)
          .height(22.dp)
          .background(MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA)),
      )
    }
  }
}
