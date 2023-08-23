package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min

@Stable
class ScreenSwipeState {
  var contentSize by mutableStateOf(IntSize(100, 100))
  var offsetX by mutableStateOf(0f)

  val stretchPercentage by derivedStateOf { offsetToPercentage(offsetX / contentSize.width) }
  val stretchIndicatorColorAlpha by derivedStateOf { min(1f, stretchPercentage) }

  val pathOffsetX by derivedStateOf { stretchPercentage * contentSize.width * PATH_OFFSET_RATE }
  val progressOffsetX by derivedStateOf { (stretchPercentage * contentSize.width * PROGRESS_OFFSET_RATE).toInt() }
  val pageOffsetX by derivedStateOf { (stretchPercentage * contentSize.width * PAGE_OFFSET_RATE).toInt() }

  companion object {
    private fun offsetToPercentage(x: Float) = log10(x + 1) * 6

    private const val PATH_OFFSET_RATE = 0.15f
    private const val PROGRESS_OFFSET_RATE = PATH_OFFSET_RATE / 2.5f
    private const val PAGE_OFFSET_RATE = PATH_OFFSET_RATE * 0.7f
  }
}

fun Modifier.withSwipeForNewItem(state: ScreenSwipeState): Modifier =
  this
    .onSizeChanged { state.contentSize = it }
    .pointerInput(Unit) {
      detectHorizontalDragGestures(
        onDragEnd = { state.offsetX = 0f },
        onDragCancel = { state.offsetX = 0f },
        onHorizontalDrag = { _, dragAmount ->
          state.offsetX = max(0f, state.offsetX + dragAmount)
        },
      )
    }
