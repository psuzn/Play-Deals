package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun LazyImage(
  model: Any,
  modifier: Modifier = Modifier,
  contentDescription: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null,
) {
  KamelImage(
    resource = asyncPainterResource(data = model),
    contentDescription = contentDescription,
    contentScale = contentScale,
    alignment = alignment,
    alpha = alpha,
    colorFilter = colorFilter,
    onLoading = { percentage ->
      if (percentage <= 0f) {
        CircularProgressIndicator(
          modifier =
            Modifier
              .align(Alignment.Center)
              .size(32.dp)
              .alpha(0.2f),
        )
      } else {
        CircularProgressIndicator(
          progress = percentage,
          backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
          modifier =
            Modifier
              .align(Alignment.Center)
              .size(32.dp)
              .alpha(0.2f),
        )
      }
    },
    onFailure = {
      Icon(
        Icons.Rounded.Warning,
        "Error",
        Modifier
          .size(32.dp)
          .alpha(0.2f)
          .align(Alignment.Center),
      )
    },
    modifier = modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.05f)),
    animationSpec = null,
  )
}
