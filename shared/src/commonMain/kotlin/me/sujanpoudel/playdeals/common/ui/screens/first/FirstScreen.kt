package me.sujanpoudel.playdeals.common.ui.screens.first

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.Screens
import me.sujanpoudel.playdeals.common.navigation.LocalNavigator
import me.sujanpoudel.playdeals.common.viewModel.viewModel


@Composable
fun FirstScreen() {

    val navigator = LocalNavigator.current

    val viewModel = viewModel<FirstScreenViewModel>()

    Scaffold {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                "Screen A: $viewModel",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(2.dp),
            )

            Spacer(Modifier.height(20.dp))

            Button(onClick = { navigator.push(Screens.SCREEN_A) }) {
                Text("Screen A", color = MaterialTheme.colors.onPrimary)
            }

            Spacer(Modifier.height(50.dp))

            Button(onClick = { navigator.push(Screens.SCREEN_B) }) {
                Text("Screen B", color = MaterialTheme.colors.onPrimary)
            }

            Spacer(Modifier.height(50.dp))

            Button(onClick = { navigator.push(Screens.THEME_SCREEN) }) {
                Text("Theme", color = MaterialTheme.colors.onPrimary)
            }

            Spacer(Modifier.height(55.dp))

            Button(onClick = { navigator.pop() }) {
                Text("Back", color = MaterialTheme.colors.onPrimary)
            }
        }

    }
}