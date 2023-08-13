package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.home.AppDealContent
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Error
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Loading
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.ScreenSwipeIndicator
import me.sujanpoudel.playdeals.common.ui.components.home.ScreenSwipeState
import me.sujanpoudel.playdeals.common.ui.components.home.withScreenSwipeIndicator
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun HomeScreen() {
  val viewModel = viewModel<HomeScreenViewModel>()
  val swipeState = remember { ScreenSwipeState() }

  Box(
    modifier =
    Modifier
      .withScreenSwipeIndicator(swipeState),
  ) {
    Scaffold(
      title = Strings.APP_DEALS,
      topRightAction = { navigator ->
        HomeScreen.NavPlusButton {
          navigator.push(Screens.NEW_DEAL)
        }
      },
      modifier =
      Modifier
        .offset { IntOffset(swipeState.pageOffsetX, 0) },
    ) { navigator, scaffoldState ->

      val openSecondScreen = remember(swipeState.stretchPercentage) { swipeState.stretchPercentage >= 1f }

      LaunchedEffect(openSecondScreen) {
        if (openSecondScreen) {
          delay(300)
          navigator.push(Screens.NEW_DEAL)
        }
      }

      val state by viewModel.state.collectAsState()

      LaunchedEffect(state.errorOneOff) {
        state.errorOneOff?.let {
          scaffoldState.snackbarHostState.showSnackbar(it, Strings.HomeScreen.RETRY)
          viewModel.clearErrorOneOff()
        }
      }

      when {
        state.persistentError != null -> Error(state.persistentError!!, viewModel::refreshDeals)
        state.isLoading -> Loading()
        state.allAppDeals.isEmpty() -> HomeScreen.NoDeals( refreshBy = viewModel::refreshDeals)
        else -> AppDealContent(state, viewModel::refreshDeals, viewModel::toggleFilterItem)
      }
    }

    ScreenSwipeIndicator(swipeState)
  }
}
