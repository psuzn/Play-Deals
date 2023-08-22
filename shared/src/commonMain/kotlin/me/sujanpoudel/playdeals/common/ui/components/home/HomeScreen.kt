package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.extensions.drawQuad
import me.sujanpoudel.playdeals.common.ui.components.common.clickableBoundless

object HomeScreen {
  @Composable
  fun NavPlusButton(onClick: () -> Unit) {
    Icon(
      Icons.Outlined.Add,
      contentDescription = Strings.HomeScreen.ADD,
      tint = MaterialTheme.colors.onBackground,
      modifier =
        Modifier.padding(8.dp)
          .clickableBoundless(true, onClick),
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
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.error,
      )

      Spacer(
        modifier = Modifier.height(12.dp),
      )

      Button(onRetry) {
        Text(Strings.HomeScreen.TRY_AGAIN)
      }
    }
  }

  @Composable
  fun NoDeals(
    modifier: Modifier = Modifier,
    refreshBy: () -> Unit,
  ) {
    Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        "Such an emptiness",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground,
      )

      Spacer(
        modifier = Modifier.height(18.dp),
      )

      Button(refreshBy) {
        Text(Strings.HomeScreen.REFRESH)
      }
    }
  }

  @Composable
  fun BoxScope.PriceButton(
    normalPrice: String,
    currentPrice: String,
    onClick: () -> Unit,
  ) {
    Button(
      onClick = onClick,
      modifier =
        Modifier.align(Alignment.BottomEnd)
          .padding(end = 16.dp),
    ) {
      Text(
        text = normalPrice,
        textDecoration = TextDecoration.LineThrough,
        color = MaterialTheme.colors.onPrimary,
      )

      Spacer(modifier = Modifier.width(4.dp))

      Text(
        text = currentPrice,
        color = MaterialTheme.colors.onPrimary,
      )
    }
  }

  @OptIn(ExperimentalAnimationApi::class)
  @Composable
  fun BoxScope.ScreenSwipeIndicator(swipeState: ScreenSwipeState) {
    val stretchIndicatorColor = MaterialTheme.colors.primary.copy(swipeState.stretchIndicatorColorAlpha)

    Canvas(
      modifier = Modifier.fillMaxSize(),
      onDraw = {
        val path = Path()

        val points =
          listOf(
            Offset(0f, 0f),
            Offset(0f, swipeState.contentSize.height * .33f),
            Offset(swipeState.pathOffsetX, swipeState.contentSize.height * .5f),
            Offset(0f, swipeState.contentSize.height * .67f),
            Offset(0f, swipeState.contentSize.height.toFloat()),
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
            x = swipeState.progressOffsetX,
            y = 0,
          )
        },
      contentAlignment = Alignment.Center,
    ) {
      CircularProgressIndicator(
        progress = swipeState.stretchPercentage,
        modifier =
          Modifier
            .rotate(-90f)
            .fillMaxSize(),
        strokeWidth = 3.dp,
        color =
          if (swipeState.stretchPercentage < 0.8f) {
            MaterialTheme.colors.primary
          } else {
            MaterialTheme.colors.onPrimary
          },
        strokeCap = StrokeCap.Round,
      )

      AnimatedVisibility(
        swipeState.stretchPercentage >= 1f,
        enter = fadeIn() + scaleIn(initialScale = 2f),
        exit = fadeOut(),
      ) {
        Icon(
          Icons.Filled.AddCircle,
          "",
          tint = MaterialTheme.colors.onPrimary,
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}
