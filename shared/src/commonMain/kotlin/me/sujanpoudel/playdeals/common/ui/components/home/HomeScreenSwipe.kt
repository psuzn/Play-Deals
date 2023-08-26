package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenSwipeState.Companion.DRAWER_CLOSE_THRESHOLD_PERCENTAGE
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreenSwipeState.Companion.DRAWER_OPEN_THRESHOLD_PERCENTAGE
import kotlin.math.absoluteValue
import kotlin.math.log10

enum class SwipeDirection {
  LEFT,
  RIGHT,
  NONE,
}

@Stable
class HomeScreenSwipeState {
  var swipeDirection by mutableStateOf(SwipeDirection.NONE)
  var swipeInProgress by mutableStateOf(false)
  var containerSize by mutableStateOf(IntSize(100, 100))

  var offsetX by mutableStateOf(0f)
  var actualUserSwipeOffset by mutableStateOf(0f)

  var flingVelocity by mutableStateOf(0f)
  var drawerOpened by mutableStateOf(false)

  val rightStretchPercentage by derivedStateOf {
    if (swipeDirection == SwipeDirection.LEFT) {
      0f
    } else {
      offsetToPercentage(offsetX / containerSize.width)
    }
  }

  val rightStretchIndicatorAlpha by derivedStateOf { rightStretchPercentage.coerceIn(0f, 1f) }

  val rightStretchPathOffsetX by derivedStateOf { rightStretchPercentage * containerSize.width * PATH_OFFSET_RATE }
  val rightStretchProgressIndicatorOffsetX by derivedStateOf {
    (rightStretchPercentage * containerSize.width * PROGRESS_INDICATOR_OFFSET_RATE).toInt()
  }

  private val visibleOffset by derivedStateOf {
    when (swipeDirection) {
      SwipeDirection.NONE,
      SwipeDirection.LEFT,
      -> if (swipeInProgress) {
        actualUserSwipeOffset.toInt()
      } else {
        offsetX.toInt()
      }

      SwipeDirection.RIGHT -> (rightStretchPercentage * containerSize.width * PAGE_OFFSET_RATE).toInt()
    }
  }

  val maxOffsetWithDrawerOpen by derivedStateOf { -containerSize.width * DRAWER_WIDTH_PERCENTAGE }

  suspend fun toggleDrawer() {
    if (drawerOpened) {
      offsetX = 0f
      delay(300)
      drawerOpened = false
    } else {
      offsetX = maxOffsetWithDrawerOpen
      drawerOpened = true
      swipeDirection = SwipeDirection.LEFT
    }
  }

  @Composable
  fun rememberScreenOffset(): State<IntOffset> {
    val animatedOffset = remember { Animatable(0f) }

    LaunchedEffect(swipeInProgress, visibleOffset) {
      if (swipeInProgress) {
        animatedOffset.snapTo(visibleOffset.toFloat())
      } else {
        animatedOffset.animateTo(
          visibleOffset.toFloat(),
          animationSpec = if (swipeDirection == SwipeDirection.LEFT) {
            tween(easing = LinearEasing, durationMillis = 100)
          } else {
            SpringSpec()
          },
          initialVelocity = flingVelocity,
        )
      }
    }

    return remember {
      derivedStateOf { IntOffset(animatedOffset.value.toInt(), 0) }
    }
  }

  companion object {
    private fun offsetToPercentage(x: Float) = log10(x + 1) * 6

    private const val PATH_OFFSET_RATE = 0.15f
    private const val PROGRESS_INDICATOR_OFFSET_RATE = PATH_OFFSET_RATE / 2.5f
    private const val PAGE_OFFSET_RATE = PATH_OFFSET_RATE * 0.7f
    const val DRAWER_WIDTH_PERCENTAGE = 0.65f
    const val DRAWER_OPEN_THRESHOLD_PERCENTAGE = 0.25f
    const val DRAWER_CLOSE_THRESHOLD_PERCENTAGE = 0.75f
  }
}

fun Modifier.withScreenSwipe(state: HomeScreenSwipeState): Modifier = composed {
  val coroutineScope = rememberCoroutineScope()

  val dragEnd: () -> Unit = {
    val openThreshold = state.maxOffsetWithDrawerOpen * DRAWER_OPEN_THRESHOLD_PERCENTAGE
    val closeThreshold = state.maxOffsetWithDrawerOpen * DRAWER_CLOSE_THRESHOLD_PERCENTAGE

    when {
      state.offsetX < openThreshold && !state.drawerOpened -> coroutineScope.launch { state.toggleDrawer() }
      state.offsetX > closeThreshold && state.drawerOpened -> coroutineScope.launch { state.toggleDrawer() }
      state.drawerOpened -> state.offsetX = state.maxOffsetWithDrawerOpen
      !state.drawerOpened -> state.offsetX = 0f
    }
  }

  val scrollState = rememberScrollableState { userScroll ->

    if (!state.swipeInProgress) {
      // there is some issue with nested scrolling
      return@rememberScrollableState 0f
    }

    if (state.swipeDirection == SwipeDirection.NONE) {
      state.swipeDirection = when {
        userScroll < 0 -> SwipeDirection.LEFT
        userScroll > 0 -> SwipeDirection.RIGHT
        else -> SwipeDirection.NONE
      }
    }

    val scrollAmount = if (state.swipeDirection == SwipeDirection.LEFT) { // initially dragged towards left

      val availableScrollDelta = if (userScroll < 0) {
        // dragging towards left
        state.maxOffsetWithDrawerOpen - state.offsetX
      } else {
        // dragging towards right
        state.offsetX
      }

      if (availableScrollDelta.absoluteValue > userScroll.absoluteValue) {
        userScroll
      } else {
        availableScrollDelta
      }
    } else { // drag to right
      userScroll
    }

    state.offsetX += scrollAmount
    scrollAmount
  }

  LaunchedEffect(state.offsetX) {
    if (state.offsetX == 0f) {
      state.swipeDirection = SwipeDirection.NONE
    }
  }

  LaunchedEffect(scrollState.isScrollInProgress, state.offsetX) {
    state.swipeInProgress = scrollState.isScrollInProgress
    if (scrollState.isScrollInProgress) {
      state.actualUserSwipeOffset = state.offsetX
    } else {
      state.actualUserSwipeOffset = 0f
    }
  }

  LaunchedEffect(scrollState.isScrollInProgress) {
    println("isScrollInProgress : ${scrollState.isScrollInProgress}")
    if (!scrollState.isScrollInProgress) {
      dragEnd()
    }
  }

  onSizeChanged { state.containerSize = it }
    .scrollable(
      scrollState,
      Orientation.Horizontal,
      flingBehavior = object : FlingBehavior {
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
          state.flingVelocity = initialVelocity
          return initialVelocity
        }
      },
    )
}
