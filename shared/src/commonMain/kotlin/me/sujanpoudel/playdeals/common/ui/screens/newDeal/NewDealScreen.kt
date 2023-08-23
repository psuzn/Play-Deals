package me.sujanpoudel.playdeals.common.ui.screens.newDeal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun NewDealScreen() {
  val viewModel = viewModel<NewDealScreenViewModel>()

  Scaffold(
    modifier = Modifier.background(Color.Cyan),
  ) { navigator, state ->
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxSize(),
    ) {
      Text(
        "Screen B: $viewModel",
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(2.dp),
      )

      Spacer(Modifier.height(20.dp))

      Button(onClick = { navigator.push(Screens.Home) }) {
        Text("Screen", color =MaterialTheme.colorScheme.onPrimary)
      }

      Spacer(Modifier.height(50.dp))

      Button(onClick = { navigator.push(Screens.NEW_DEAL) }) {
        Text("Screen B", color =MaterialTheme.colorScheme.onPrimary)
      }

      Spacer(Modifier.height(55.dp))

      Button(onClick = { navigator.pop() }) {
        Text("Back", color =MaterialTheme.colorScheme.onPrimary)
      }
    }
  }
}
