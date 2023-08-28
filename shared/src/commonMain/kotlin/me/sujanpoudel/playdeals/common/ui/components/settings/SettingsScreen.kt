package me.sujanpoudel.playdeals.common.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.extensions.capitalizeWords
import me.sujanpoudel.playdeals.common.ui.screens.settings.icon
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object SettingsScreen {

  @Composable
  fun SettingItem(
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

  @Composable
  fun Footer(
    onClick: () -> Unit,
    color: Color,
  ) {
    Column(
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(Strings.APP_DEALS, style = MaterialTheme.typography.titleSmall, color = color)
      Text("V1.1.5-16", style = MaterialTheme.typography.bodySmall, color = color)
    }
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Composable
  fun ChooseThemeDialog(
    showDialog: Boolean,
    closeRequest: () -> Unit,
    setAppearanceMode: (AppearanceMode) -> Unit,
  ) {
    val appearanceModes = remember { AppearanceMode.values().toSet() }

    if (showDialog) {
      Dialog(closeRequest) {
        Column(
          modifier = Modifier
            .width(IntrinsicSize.Max)
            .clip(RoundedCornerShape(5))
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 16.dp),
        ) {
          Text(
            "Theme Preference",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp, start = 54.dp, end = 54.dp),
          )

          Divider()

          appearanceModes.forEach {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                  closeRequest()
                  setAppearanceMode(it)
                })
                .padding(horizontal = 16.dp, vertical = 10.dp),
            ) {
              Text(
                text = it.name.lowercase().capitalizeWords(),
                modifier = Modifier.weight(1f).apply {
                  if (appearanceModes.last() == it) {
                    this.padding(bottom = 32.dp)
                  }
                },
                style = MaterialTheme.typography.bodyMedium,
              )
              Icon(it.icon, contentDescription = it.name, tint = MaterialTheme.colorScheme.primary)
            }

            if (appearanceModes.last() != it) {
              Divider()
            }
          }
        }
      }
    }
  }
}
