package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import me.sujanpoudel.playdeals.common.navigation.LocalNavigator
import me.sujanpoudel.playdeals.common.navigation.Navigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
  title: String? = null,
  modifier: Modifier = Modifier,
  topRightAction: (@Composable (Navigator) -> Unit)? = null,
  content: @Composable BoxScope.(Navigator, SnackbarHostState) -> Unit,
) {
  val snackBarHostState = remember { SnackbarHostState() }

  val navigator = LocalNavigator.current

  androidx.compose.material3.Scaffold(
    topBar = {
      TopAppBar(
        title = {
          title?.let {
            Text(
              text = title,
              style = MaterialTheme.typography.titleMedium,
              textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth(),
            )
          }
        },
        actions = { topRightAction?.invoke(navigator) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
          containerColor = Color.Transparent
        )
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
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier.padding(it),
    ) {
      content(navigator, snackBarHostState)
    }
  }
}
