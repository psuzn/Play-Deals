package me.sujanpoudel.playdeals.common.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.LocalAppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA
import me.sujanpoudel.playdeals.common.ui.theme.UIAppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.asUITheme

val AppearanceMode.icon: ImageVector
  @Composable
  get() = when (asUITheme()) {
    UIAppearanceMode.DARK -> Icons.Outlined.DarkMode
    UIAppearanceMode.LIGHT -> Icons.Filled.LightMode
    UIAppearanceMode.BLACK -> Icons.Filled.DarkMode
  }

@Composable
fun ThemeSwitcherScreen() {
  val appearanceManager = LocalAppearanceModeManager.current
  val appearanceMode by appearanceManager.appearanceMode.collectAsState()

  appearanceMode.asUITheme().isDark

  var notificationEnabled by remember { mutableStateOf(true) }
  var developerModeEnabled by remember { mutableStateOf(true) }

  Scaffold(
    title = Strings.SETTINGS,
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      SettingItem(
        title = "Appearance",
        description = "Choose your light ot dark theme preference",
        onClick = {
          val values = AppearanceMode.values()

          val nextValue = (values.indexOf(appearanceMode) + 1) % values.size

          appearanceManager.setAppearanceMode(values[nextValue])
        },
      ) {
        Icon(
          appearanceMode.icon,
          contentDescription = "",
        )
      }

      SettingItem(
        title = "Don't miss any deals",
        description = "Get notification for all new app deals",
        onClick = { notificationEnabled = notificationEnabled.not() },
      ) {
        Switch(
          checked = notificationEnabled,
          onCheckedChange = {
            notificationEnabled = notificationEnabled.not()
          },
        )
      }

      Spacer(Modifier.weight(1f))

      Column(
        Modifier
          .fillMaxWidth()
          .clickable { }
          .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(Strings.APP_DEALS, style = MaterialTheme.typography.titleSmall)
        Text("V1.1.5-16", style = MaterialTheme.typography.bodySmall)
      }
    }
  }
}

@Composable
private fun SettingItem(
  title: String,
  description: String,
  onClick: () -> Unit,
  rightAction: @Composable BoxScope.() -> Unit = {},
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp, vertical = 12.dp),
  ) {
    Column(
      modifier = Modifier.weight(1f),
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
      )
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(SOFT_COLOR_ALPHA),
      )
    }

    Box(
      modifier = Modifier.align(Alignment.CenterVertically),
    ) {
      rightAction()
    }
  }
}
