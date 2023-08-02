package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sujanpoudel.playdeals.common.navigation.LocalNavigator
import me.sujanpoudel.playdeals.common.navigation.Navigator

@Composable
fun ScaffoldTitle(title: String) {
  Text(
    text = title,
    style =
      MaterialTheme.typography.h3.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
      ),
    textAlign = TextAlign.Center,
    modifier = Modifier.fillMaxWidth(),
  )
}

@Composable
fun Scaffold(
  title: String? = null,
  modifier: Modifier = Modifier,
  topRightAction: (@Composable (Navigator) -> Unit)? = null,
  content: @Composable BoxScope.(Navigator, ScaffoldState) -> Unit,
) {
  val snackBarHostState = remember { SnackbarHostState() }
  val scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState)
  val navigator = LocalNavigator.current

  androidx.compose.material.Scaffold(
    topBar = {
      TopAppBar(
        title = {
          title?.let {
            Text(
              text = title,
              style =
                MaterialTheme.typography.h3.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 18.sp,
                ),
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth(),
            )
          }
        },
        actions = { topRightAction?.invoke(navigator) },
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
      )
    },
    scaffoldState = scaffoldState,
    snackbarHost = {
      SnackbarHost(it) { snackBarData ->
        Snackbar(
          snackBarData,
          backgroundColor = MaterialTheme.colors.surface,
          actionColor = MaterialTheme.colors.primary,
          contentColor = MaterialTheme.colors.onSurface,
        )
      }
    },
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier.padding(it),
    ) {
      content(navigator, scaffoldState)
    }
  }
}
