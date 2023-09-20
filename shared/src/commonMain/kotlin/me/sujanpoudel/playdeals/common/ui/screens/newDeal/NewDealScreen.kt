package me.sujanpoudel.playdeals.common.ui.screens.newDeal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun NewDealScreen() {
  val viewModel = viewModel<NewDealScreenViewModel>()
  Scaffold(
    title = Strings.addNewDeal,
  ) {
    Column(
      modifier = Modifier.align(Alignment.Center)
        .alpha(SOFT_COLOR_ALPHA),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Text(
        "Ah, Oh!!",
        style = MaterialTheme.typography.bodyMedium,
      )

      Text(
        "This is work in progress.",
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}
