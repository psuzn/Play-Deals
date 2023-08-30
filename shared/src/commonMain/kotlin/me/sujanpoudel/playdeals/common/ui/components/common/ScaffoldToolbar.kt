package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import me.sujanpoudel.playdeals.common.navigation.Navigator

object ScaffoldToolbar {

  @Composable
  fun NavigationIcon(navigator: Navigator) {
    IconButton(onClick = navigator::pop) {
      Icon(Icons.Default.ArrowBack, contentDescription = "")
    }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  operator fun invoke(
    modifier: Modifier = Modifier,
    title: String? = null,
    showNavBackIcon: Boolean = true,
    navigationIcon: @Composable (Navigator) -> Unit = { it -> NavigationIcon(it) },
    actions: (@Composable (Navigator) -> Unit)? = null,
    behaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
  ) {
    val navigator = Navigator.current

    CenterAlignedTopAppBar(
      modifier = modifier,
      title = {
        title?.let {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Center,
          )
        }
      },
      navigationIcon = {
        if (showNavBackIcon && navigator.backStackCount.value > 1) {
          navigationIcon(navigator)
        }
      },
      actions = { actions?.invoke(navigator) },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
      ),
      scrollBehavior = behaviour,
    )
  }
}
