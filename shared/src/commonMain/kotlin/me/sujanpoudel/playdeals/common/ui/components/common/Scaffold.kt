package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.sujanpoudel.playdeals.common.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
  modifier: Modifier = Modifier,
  title: String? = null,
  showNavBackIcon: Boolean = true,
  actions: (@Composable (Navigator) -> Unit)? = null,
  content: @Composable BoxScope.() -> Unit,
) {
  val navigator = Navigator.current

  androidx.compose.material3.Scaffold(
    topBar = {
      ScaffoldToolbar(
        title = title,
        showNavBackIcon = showNavBackIcon,
        actions = actions,
      )
    },
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier.padding(it),
    ) {
      content()
    }
  }
}
