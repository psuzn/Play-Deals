package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.extensions.drawQuad
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.clickableBoundless

object HomeScreen {
  @Composable
  fun NavMenu(onClick: () -> Unit) {
    Icon(
      Icons.Outlined.Menu,
      contentDescription = "",
      tint = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier.padding(8.dp)
        .clickableBoundless(onclick = onClick),
    )
  }

  @Composable
  fun Loading() {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {
      CircularProgressIndicator()
    }
  }

  @Composable
  fun Error(
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
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error,
      )

      Spacer(
        modifier = Modifier.height(12.dp),
      )

      Button(onRetry) {
        Text(Strings.tryAgain)
      }
    }
  }

  @Composable
  fun BoxScope.RightSwipeIndicator(swipeState: HomeScreenSwipeState) {
    val stretchIndicatorColor = MaterialTheme.colorScheme.primary.copy(swipeState.rightStretchIndicatorAlpha)

    Canvas(
      modifier = Modifier.fillMaxSize(),
      onDraw = {
        val path = Path()

        val points =
          listOf(
            Offset(0f, 0f),
            Offset(0f, swipeState.containerSize.height * .33f),
            Offset(swipeState.rightStretchPathOffsetX, swipeState.containerSize.height * .5f),
            Offset(0f, swipeState.containerSize.height * .67f),
            Offset(0f, swipeState.containerSize.height.toFloat()),
          )

        points.forEachIndexed { index, item ->
          if (index == 0) {
            path.moveTo(item.x, item.y)
          } else {
            path.drawQuad(points, index, item)
          }
        }
        drawPath(path, stretchIndicatorColor)
      },
    )

    Box(
      Modifier.size(26.dp)
        .align(BiasAlignment(-1.1f, 0f))
        .offset {
          IntOffset(
            x = swipeState.rightStretchProgressIndicatorOffsetX,
            y = 0,
          )
        },
      contentAlignment = Alignment.Center,
    ) {
      CircularProgressIndicator(
        progress = swipeState.rightStretchPercentage,
        modifier = Modifier
          .rotate(-90f)
          .fillMaxSize(),
        strokeWidth = 3.dp,
        color = if (swipeState.rightStretchPercentage < 0.8f) {
          MaterialTheme.colorScheme.primary
        } else {
          MaterialTheme.colorScheme.onPrimary
        },
      )

      AnimatedVisibility(
        swipeState.rightStretchPercentage >= 1f,
        enter = fadeIn() + scaleIn(initialScale = 2f),
        exit = fadeOut(),
      ) {
        Icon(
          Icons.Filled.AddCircle,
          "",
          tint = MaterialTheme.colorScheme.onPrimary,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
