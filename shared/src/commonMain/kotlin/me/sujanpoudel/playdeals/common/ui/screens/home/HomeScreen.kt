package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.navigation.NavTransitions
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.ScaffoldToolbar
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.PullRefreshIndicator
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.pullRefresh
import me.sujanpoudel.playdeals.common.ui.components.common.pullToRefresh.rememberPullRefreshState
import me.sujanpoudel.playdeals.common.ui.components.home.AppDealContent
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Error
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.Loading
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.RightSwipeIndicator
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenDrawer
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenDrawer.DrawerBackDrop
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenSwipeState
import me.sujanpoudel.playdeals.common.ui.components.home.withScreenSwipe
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
  val viewModel = viewModel<HomeScreenViewModel>()
  val swipeState = remember { HomeScreenSwipeState() }
  val state by viewModel.state.collectAsState()
  val pullToRefreshState = rememberPullRefreshState(state.isRefreshing, viewModel::refreshDeals)
  val screenOffset by swipeState.rememberScreenOffset()
  val coroutineScope = rememberCoroutineScope()

  val uriHandler = LocalUriHandler.current
  val navigator = Navigator.current

  val strings = Strings

  LaunchedEffect(state.destinationOneOff) {
    state.destinationOneOff?.also {
      viewModel.clearOneOffDestination()
      navigator.navigateToChangelog()
    }
  }

  Box(
    modifier = Modifier.withScreenSwipe(swipeState),
  ) {
    val snackBarHostState = remember { SnackbarHostState() }
    val topBarState = rememberTopAppBarState()
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(topBarState)

    Scaffold(
      topBar = {
        ScaffoldToolbar(
          title = Strings.appDeals,
          behaviour = topBarScrollBehaviour,
          actions = {
            HomeScreen.NavMenu {
              coroutineScope.launch {
                swipeState.toggleDrawer()
              }
            }
          },
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
        .offset { screenOffset }
        .pullRefresh(pullToRefreshState)
        .nestedScroll(topBarScrollBehaviour.nestedScrollConnection),
    ) { paddingValues ->

      Box(
        modifier = Modifier
          .padding(paddingValues),
      ) {
        val openSecondScreen = remember(swipeState.rightStretchPercentage) {
          swipeState.rightStretchPercentage >= 1f
        }

        LaunchedEffect(openSecondScreen) {
          if (openSecondScreen) {
            delay(300)
            navigator.push(Screens.NEW_DEAL)
          }
        }

        LaunchedEffect(state.errorOneOff) {
          state.errorOneOff?.let {
            snackBarHostState.showSnackbar(it, strings.retry)
            viewModel.clearErrorOneOff()
          }
        }

        when {
          state.persistentError != null -> Error(state.persistentError!!, viewModel::refreshDeals)
          state.isLoading -> Loading()
          else -> AppDealContent(
            state,
            onToggleFilterOption = viewModel::toggleFilterItem,
            refreshAppDeals = viewModel::refreshDeals,
          )
        }
      }
    }

    RightSwipeIndicator(swipeState)

    PullRefreshIndicator(
      modifier = Modifier.align(BiasAlignment(0f, -0.9f)),
      refreshing = state.isRefreshing,
      state = pullToRefreshState,
      scale = true,
      contentColor = MaterialTheme.colorScheme.primary,
    )

    HomeScreenDrawer(
      modifier = Modifier.absoluteOffset { IntOffset(swipeState.containerSize.width + screenOffset.x, 0) }
        .width(
          with(LocalDensity.current) {
            swipeState.containerSize.width.toDp().times(HomeScreenSwipeState.DRAWER_WIDTH_PERCENTAGE)
          },
        ),
      onMenuClicked = {
        when (it) {
          HomeScreenDrawer.Menu.SETTINGS -> navigator.push(Screens.SETTINGS)
          HomeScreenDrawer.Menu.WHAT_NEW -> navigator.navigateToChangelog()

          HomeScreenDrawer.Menu.FOOTER -> uriHandler.openUri(Constants.ABOUT_ME_URL)
        }

        coroutineScope.launch {
          if (it != HomeScreenDrawer.Menu.WHAT_NEW) {
            delay(1000)
          }
          swipeState.toggleDrawer()
        }
      },
    )

    DrawerBackDrop(
      isVisible = swipeState.drawerOpened,
      onClick = {
        coroutineScope.launch { swipeState.toggleDrawer() }
      },
      modifier = Modifier
        .offset { screenOffset },
    )
  }
}

fun Navigator.navigateToChangelog() {
  push(
    Screens.CHANGELOG,
    enterTransition = NavTransitions.slideInFromBottom,
    exitTransition = NavTransitions.EmptyExitTransition,
    popEnter = NavTransitions.EmptyEnterTransition,
    popExit = NavTransitions.slideOutToBottom,
  )
}
