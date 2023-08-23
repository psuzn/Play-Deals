package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.PullRefreshIndicator
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.pullRefresh
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.rememberPullRefreshState
import me.sujanpoudel.playdeals.common.ui.components.home.AppDealContent
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Error
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Loading
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.SwipeForNewItemIndicator
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenSwipeState
import me.sujanpoudel.playdeals.common.ui.components.home.withScreenSwipe
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
  val viewModel = viewModel<HomeScreenViewModel>()
  val homeScreenSwipeState = remember { HomeScreenSwipeState() }
  val state by viewModel.state.collectAsState()
  val pullToRefreshState = rememberPullRefreshState(state.isRefreshing, viewModel::refreshDeals)

  val animatedOffset by animateOffsetAsState(IntOffset(homeScreenSwipeState.screenOffsetX, 0).toOffset())

  val coroutineScope = rememberCoroutineScope()

  Box {
    val snackBarHostState = remember { SnackbarHostState() }
    val topBarState = rememberTopAppBarState()
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)
    val navigator: Navigator = Navigator.current

    Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
          title = {
            Text(
              text = Strings.APP_DEALS,
              style = MaterialTheme.typography.titleMedium,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth(),
            )
          },
          actions = {
            HomeScreen.NavMenu {
              coroutineScope.launch {
                homeScreenSwipeState.toggleRightMenu()
              }
            }
          },
          colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
          scrollBehavior = topBarScrollBehaviour,
        )
      },
      snackbarHost = {
        SnackbarHost(snackBarHostState) { snackBarData ->
          Snackbar(
            snackBarData,
            containerColor = MaterialTheme.colorScheme.surface,
            actionColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface,
          )
        }
      },
      modifier = Modifier
        .offset {
          if (homeScreenSwipeState.rightMenuOpened) {
            IntOffset(animatedOffset.x.toInt(), 0)
          } else {
            IntOffset(homeScreenSwipeState.screenOffsetX, 0)
          }
        }
        .pullRefresh(pullToRefreshState)
        .nestedScroll(topBarScrollBehaviour.nestedScrollConnection)
        .withScreenSwipe(homeScreenSwipeState),
    ) { paddingValues ->

      Box(
        modifier = Modifier.fillMaxSize()
          .padding(paddingValues),
      ) {
        val openSecondScreen =
          remember(homeScreenSwipeState.stretchPercentage) { homeScreenSwipeState.stretchPercentage >= 1f }

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
          else -> AppDealContent(
            state,
            onToggleFilterOption = viewModel::toggleFilterItem,
          )
        }
      }
    }

    SwipeForNewItemIndicator(homeScreenSwipeState)
    PullRefreshIndicator(
      modifier = Modifier.align(BiasAlignment(0f, -0.9f)),
      refreshing = state.isRefreshing,
      state = pullToRefreshState,
      scale = true,
      contentColor = MaterialTheme.colorScheme.primary,
    )
  }
}
