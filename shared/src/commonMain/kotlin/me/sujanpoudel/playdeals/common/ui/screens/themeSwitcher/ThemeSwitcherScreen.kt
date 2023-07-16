package me.sujanpoudel.playdeals.common.ui.screens.themeSwitcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.sujanpoudel.playdeals.common.ui.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.LocalAppearanceManager
import me.sujanpoudel.playdeals.common.ui.asUITheme
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun ThemeSwitcherScreen() {

    val appearanceManager = LocalAppearanceManager.current

    val appearanceMode by appearanceManager.appearanceMode.collectAsState()

    val viewModel = viewModel<ThemeSwitcherViewModel>()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Play Deals : $appearanceMode : ${appearanceMode.asUITheme()}")

            Button(onClick = { appearanceManager.setAppearanceMode(AppearanceMode.BLACK) }) {
                Text("Black Theme")
            }

            Button(onClick = { appearanceManager.setAppearanceMode(AppearanceMode.DARK) }) {
                Text("Dark Theme")
            }

            Button(onClick = { appearanceManager.setAppearanceMode(AppearanceMode.LIGHT) }) {
                Text("Light Theme")
            }

            Button(onClick = { appearanceManager.setAppearanceMode(AppearanceMode.SYSTEM) }) {
                Text("System ")
            }
        }

    }

}