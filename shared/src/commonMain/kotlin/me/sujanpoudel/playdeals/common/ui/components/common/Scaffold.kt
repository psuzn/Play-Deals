package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.sujanpoudel.playdeals.common.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
  modifier: Modifier = Modifier,
  title: ScaffoldToolbar.ScaffoldTitle = ScaffoldToolbar.ScaffoldTitle.None,
  showNavIcon: Boolean = true,
  navigationIcon: @Composable (Navigator) -> Unit = { it -> ScaffoldToolbar.NavigationIcon(it) },
  actions: (@Composable (Navigator) -> Unit)? = null,
  content: @Composable BoxScope.() -> Unit,
) {
  androidx.compose.material3.Scaffold(
    topBar = {
      ScaffoldToolbar(
        title = title,
        showNavIcon = showNavIcon,
        actions = actions,
        navigationIcon = navigationIcon,
      )
    },
    modifier = modifier,
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(it),
    ) {
      content()
    }
  }
}
