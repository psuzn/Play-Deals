package me.sujanpoudel.playdeals.common.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.domain.models.DealFilterOption
import me.sujanpoudel.playdeals.common.navigation.LocalNavigator
import me.sujanpoudel.playdeals.common.ui.components.common.LazyImage
import me.sujanpoudel.playdeals.common.ui.components.common.clickableBoundless
import me.sujanpoudel.playdeals.common.viewModel.viewModel


const val SOFT_COLOR_ALPHA = 0.4f

@Composable
fun FirstScreen() {

  val snackBarHostState = remember { SnackbarHostState() }
  val scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
  val navigator = LocalNavigator.current

  val viewModel = viewModel<HomeScreenViewModel>()

  val state by viewModel.state.collectAsState()

  LaunchedEffect(state.errorOneOff) {
    state.errorOneOff?.let {
      snackBarHostState.showSnackbar(it, "Retry")
      viewModel.clearErrorOneOff()
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "App Deals",
            style = MaterialTheme.typography.h3.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
          )
        },

        actions = {
          Icon(
            Icons.Outlined.Add, "Add",
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(8.dp)
              .clickableBoundless(true) {
                navigator.push(Screens.NEW_DEAL)
              }
          )
        },

        elevation = 0.dp,
        backgroundColor = Color.Transparent
      )
    },

    scaffoldState = scaffoldState,
    snackbarHost = {
      SnackbarHost(it) { snackBarData ->
        Snackbar(
          snackBarData,
          backgroundColor = MaterialTheme.colors.surface,
          actionColor = MaterialTheme.colors.primary,
          contentColor = MaterialTheme.colors.onSurface
        )
      }
    }
  ) { paddingValues ->

    Box(
      modifier = Modifier.padding(paddingValues)
    ) {

      when {
        state.persistentError != null -> ErrorMessageContent(
          message = state.persistentError!!,
          onRetry = viewModel::refreshDeals
        )

        state.isLoading -> FullScreenLoadingContent()
        else -> AppDealsContent(
          state = state,
          onRequestRefresh = viewModel::refreshDeals,
          onToggleFilterOption = viewModel::toggleFilterItem
        )
      }
    }
  }
}

@Composable
private fun BoxScope.FullScreenLoadingContent() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@Composable
private fun BoxScope.ErrorMessageContent(
  message: String,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      message,
      style = MaterialTheme.typography.body1,
      color = MaterialTheme.colors.error
    )

    Spacer(
      modifier = Modifier.height(12.dp)
    )

    Button(onRetry) {
      Text("Try Again")
    }
  }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BoxScope.AppDealsContent(
  state: HomeScreenState,
  onRequestRefresh: () -> Unit,
  onToggleFilterOption: (DealFilterOption) -> Unit,
) {
  val pullToRefreshState = rememberPullRefreshState(state.isRefreshing, onRequestRefresh)

  Box(
    modifier = Modifier.fillMaxSize()
      .pullRefresh(pullToRefreshState)
  ) {

    Column {

      if (state.filterOptions.isNotEmpty()) {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {

          itemsIndexed(state.filterOptions) { index, it ->
            FilterChip(
              onClick = { onToggleFilterOption(it.data) },
              selected = it.selected,
              colors = ChipDefaults.filterChipColors(
                selectedBackgroundColor = MaterialTheme.colors.primary
              )
            ) {
              Text(
                text = it.data.label,
                color = if (it.selected) MaterialTheme.colors.onPrimary
                else MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1
              )
            }

            if (index == 1) {
              Box(
                modifier = Modifier
                  .padding(start = 8.dp)
                  .width(1.dp)
                  .height(22.dp)
                  .background(MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA))
              )
            }
          }
        }
      }

      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(state.dealsToDisplay, key = { it.id }) {
          AppDealItem(it)
        }
      }

    }

    PullRefreshIndicator(
      state.isRefreshing,
      pullToRefreshState,
      Modifier.align(Alignment.TopCenter),
      scale = true,
      contentColor = MaterialTheme.colors.primary
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.AppDealItem(appDeal: AppDeal) {

  Box(
    Modifier
      .animateItemPlacement()
      .fillMaxWidth()
      .background(MaterialTheme.colors.background)
  ) {

    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {

      val state = rememberLazyListState()

      Box {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          state = state,
          flingBehavior = rememberSnapFlingBehavior(state),
          contentPadding = PaddingValues(horizontal = 16.dp),
          modifier = Modifier.aspectRatio(2.22f, false)
        ) {

          items(appDeal.images) {
            LazyImage(
              model = it,
              contentDescription = appDeal.name,
              modifier = Modifier.fillMaxHeight()
                .clip(RoundedCornerShape(percent = 5))
                .defaultMinSize(minWidth = 100.dp)
                .animateContentSize(animationSpec = tween(500)),
              contentScale = ContentScale.FillHeight,
            )
          }
        }

        Row(
          modifier = Modifier
            .padding(bottom = 6.dp, end = 6.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.primary)
            .align(Alignment.BottomEnd)
            .padding(vertical = 4.dp, horizontal = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            Icons.Outlined.Info, "New item",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = "new",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 1.dp)
          )
        }
      }

      Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)
          .height(80.dp)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {

        LazyImage(
          model = appDeal.icon,
          contentDescription = appDeal.name,
          modifier = Modifier.fillMaxHeight()
            .aspectRatio(1f, true)
            .fillMaxSize()
            .clip(RoundedCornerShape(percent = 10)),
          contentScale = ContentScale.Inside,
        )

        Column(
          verticalArrangement = Arrangement.spacedBy(2.2.dp),
          modifier = Modifier.align(Alignment.CenterVertically)
        ) {
          Text(
            appDeal.name,
            style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
          )

          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(
              appDeal.category,
              style = MaterialTheme.typography.body1,
              color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )

            Box(
              Modifier
                .padding(horizontal = 2.dp)
                .size(4.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(percent = 100))
                .background(MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA))
            )

            Icon(
              Icons.Default.SaveAlt,
              contentDescription = "Download",
              modifier = Modifier.size(14.dp),
              tint = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )

            Text(
              appDeal.downloads,
              style = MaterialTheme.typography.body1,
              color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              appDeal.ratingFormatted,
              style = MaterialTheme.typography.body1,
              color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )

            Icon(
              Icons.Outlined.Star, "Ratings",
              modifier = Modifier.size(14.dp),
              tint = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )

            Text(
              "Expires in 2 days",
              style = MaterialTheme.typography.body1,
              color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              "r/googlePlayDeals",
              style = MaterialTheme.typography.body1,
              color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
            )
          }
        }
      }
    }

    Button(
      onClick = {},
      modifier = Modifier.align(Alignment.BottomEnd)
        .padding(end = 16.dp),
    ) {

      Text(
        text = appDeal.formattedNormalPrice,
        textDecoration = TextDecoration.LineThrough,
        color = MaterialTheme.colors.onPrimary
      )

      Spacer(modifier = Modifier.width(4.dp))

      Text(
        text = appDeal.formattedCurrentPrice,
        color = MaterialTheme.colors.onPrimary
      )
    }
  }
}
