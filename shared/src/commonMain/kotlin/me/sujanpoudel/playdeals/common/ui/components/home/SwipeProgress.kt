package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min

@Stable
class HomeScreenSwipeState {
  var contentSize by mutableStateOf(IntSize(100, 100))
  var offsetX by mutableStateOf(0f)
  var drawerOpened by mutableStateOf(false)

  val stretchPercentage by derivedStateOf {
    if (drawerOpened) {
      0f
    } else {
      offsetToPercentage(offsetX / contentSize.width)
    }
  }

  val stretchIndicatorColorAlpha by derivedStateOf { max(0f, min(1f, stretchPercentage)) }

  val pathOffsetX by derivedStateOf { stretchPercentage * contentSize.width * PATH_OFFSET_RATE }
  val progressOffsetX by derivedStateOf { (stretchPercentage * contentSize.width * PROGRESS_OFFSET_RATE).toInt() }
  val screenOffsetX by derivedStateOf {
    if (drawerOpened) {
      offsetX.toInt()
    } else {
      (stretchPercentage * contentSize.width * PAGE_OFFSET_RATE).toInt()
    }
  }

  val maxOffsetWithDrawerOpen by derivedStateOf { -contentSize.width * DRAWER_WIDTH }

  suspend fun toggleDrawer() {
    if (drawerOpened) {
      offsetX = 0f
      delay(300)
      drawerOpened = false
    } else {
      offsetX = -contentSize.width * DRAWER_WIDTH
      drawerOpened = true
    }
  }

  companion object {
    private fun offsetToPercentage(x: Float) = log10(x + 1) * 6

    private const val PATH_OFFSET_RATE = 0.15f
    private const val PROGRESS_OFFSET_RATE = PATH_OFFSET_RATE / 2.5f
    private const val PAGE_OFFSET_RATE = PATH_OFFSET_RATE * 0.7f
    const val DRAWER_WIDTH = 0.75f
  }
}

fun Modifier.withScreenSwipe(state: HomeScreenSwipeState): Modifier = composed {
  val coroutineScope = rememberCoroutineScope()

  onSizeChanged { state.contentSize = it }
    .pointerInput(Unit) {
      if (state.drawerOpened.not()) {
        detectHorizontalDragGestures(
          onDragEnd = {
            val openThreshold = state.maxOffsetWithDrawerOpen * 0.25f
            val closeThreshold = state.maxOffsetWithDrawerOpen * 0.75f

            if (state.offsetX < openThreshold && !state.drawerOpened) {
              coroutineScope.launch { state.toggleDrawer() }
            } else if (state.offsetX > closeThreshold && state.drawerOpened) {
              coroutineScope.launch { state.toggleDrawer() }
            } else {
              state.offsetX = 0f
            }
          },
          onDragCancel = {
            if (!state.drawerOpened) {
              state.offsetX = 0f
            }
          },
          onHorizontalDrag = { _, dragAmount ->
            state.offsetX = max(state.maxOffsetWithDrawerOpen, state.offsetX + dragAmount)
          },
        )
      }
    }
}
