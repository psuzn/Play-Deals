package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sujanpoudel.playdeals.common.ui.components.common.clickableWithoutIndicator
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object HomeScreenDrawer {
  @Composable
  operator fun invoke(
    modifier: Modifier = Modifier,
  ) {
    Column(
      modifier = modifier
        .fillMaxHeight()
        .background(MaterialTheme.colorScheme.outlineVariant)
        .padding(start = 1.dp)
        .background(MaterialTheme.colorScheme.background),
    ) {
      DrawerTitle()

      Divider(thickness = 1.dp)

      Column(
        modifier = Modifier
          .weight(1f),
      ) {
        DrawerMenuItem("Settings", Icons.Outlined.Settings, {})
        DrawerMenuItem("What's New", Icons.Outlined.Info, {})
      }

      Divider(thickness = 1.dp)

      DrawerFooter({})
    }
  }

  @Composable
  private fun DrawerTitle() {
    Text(
      text = "More Info",
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .height(56.dp)
        .wrapContentHeight(),
    )
  }

  @Composable
  private fun DrawerMenuItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
      modifier = Modifier.height(56.dp)
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp),
    ) {
      Icon(
        icon,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.onBackground,
      )

      Text(
        text = label,
        style = MaterialTheme.typography.titleSmall,
      )
    }
  }

  @Composable
  private fun DrawerFooter(onClick: () -> Unit) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      modifier = Modifier
        .height(56.dp)
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp),
    ) {
      Text(
        text = "About me",
        style = MaterialTheme.typography.titleSmall,
      )

      Icon(
        Icons.Default.OpenInNew,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.onBackground.copy(SOFT_COLOR_ALPHA),
      )
    }
  }

  @Composable
  fun DrawerBackDrop(
    isDrawerOpen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
  ) {
    AnimatedVisibility(
      isDrawerOpen,
      enter = EnterTransition.None,
      exit = ExitTransition.None,
    ) {
      Box(
        modifier = modifier.fillMaxSize()
          .clickableWithoutIndicator { onClick.invoke() },
      )
    }
  }
}
