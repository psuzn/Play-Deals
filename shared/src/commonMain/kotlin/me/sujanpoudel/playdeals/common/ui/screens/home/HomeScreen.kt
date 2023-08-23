package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.PullRefreshIndicator
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.pullRefresh
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.rememberPullRefreshState
import me.sujanpoudel.playdeals.common.ui.components.home.AppDealContent
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Error
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Loading
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.SwipeForNewItemIndicator
import me.sujanpoudel.playdeals.common.ui.components.home.ScreenSwipeState
import me.sujanpoudel.playdeals.common.ui.components.home.withSwipeForNewItem
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun HomeScreen() {
  val viewModel = viewModel<HomeScreenViewModel>()
  val swipeState = remember { ScreenSwipeState() }
  val state by viewModel.state.collectAsState()
  val pullToRefreshState = rememberPullRefreshState(state.isRefreshing, viewModel::refreshDeals)

  Box(
    modifier = Modifier
      .pullRefresh(pullToRefreshState)
      .withSwipeForNewItem(swipeState),
  ) {
    Scaffold(
      title = Strings.APP_DEALS,
      topRightAction = { navigator ->
        HomeScreen.NavPlusButton {
          navigator.push(Screens.NEW_DEAL)
        }
      },
      modifier = Modifier
        .offset { IntOffset(swipeState.pageOffsetX, 0) },
    ) { navigator, snackBarHostState ->

      val openSecondScreen = remember(swipeState.stretchPercentage) { swipeState.stretchPercentage >= 1f }

      LaunchedEffect(openSecondScreen) {
        if (openSecondScreen) {
          delay(300)
          navigator.push(Screens.NEW_DEAL)
        }
      }

      LaunchedEffect(state.errorOneOff) {
        state.errorOneOff?.let {
          snackBarHostState.showSnackbar(it, Strings.HomeScreen.RETRY)
          viewModel.clearErrorOneOff()
        }
      }

      when {
        state.persistentError != null -> Error(state.persistentError!!, viewModel::refreshDeals)
        state.isLoading -> Loading()
        state.allAppDeals.isEmpty() -> HomeScreen.NoDeals(refreshBy = viewModel::refreshDeals)
        else -> AppDealContent(state, viewModel::refreshDeals, viewModel::toggleFilterItem)
      }
    }

    SwipeForNewItemIndicator(swipeState)
    PullRefreshIndicator(
      modifier = Modifier.align(BiasAlignment(0f, -0.9f)),
      refreshing = state.isRefreshing,
      state = pullToRefreshState,
      scale = true,
      contentColor = MaterialTheme.colorScheme.primary,
    )
  }
}
