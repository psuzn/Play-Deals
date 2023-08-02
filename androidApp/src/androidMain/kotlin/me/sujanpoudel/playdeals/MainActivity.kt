package me.sujanpoudel.playdeals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import me.sujanpoudel.playdeals.common.PlayDealsAppAndroid
import me.sujanpoudel.playdeals.common.navigation.BackPressConsumer
import me.sujanpoudel.playdeals.common.navigation.LocalBackPressConsumer
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager
import me.sujanpoudel.playdeals.common.ui.theme.asUITheme
import me.sujanpoudel.playdeals.utils.rememberSystemUiController

class MainActivity : ComponentActivity() {
  private val callBack =
    object : OnBackPressedCallback(false) {
      override fun handleOnBackPressed() {
        backPressConsumer.onBackPress()
      }
    }

  private val backPressConsumer: BackPressConsumer =
    BackPressConsumer {
      callBack.isEnabled = it
    }

  private val appearanceModeManager = AppearanceModeManager()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    onBackPressedDispatcher.addCallback(callBack)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val systemUiController = rememberSystemUiController()

      val themeMode by appearanceModeManager.appearanceMode.collectAsState()

      val uiTheme = themeMode.asUITheme()

      LaunchedEffect(uiTheme) {
        systemUiController.setSystemBarsColor(Color.Transparent, !uiTheme.isDark)
      }

      CompositionLocalProvider(
        LocalBackPressConsumer provides backPressConsumer,
      ) {
        PlayDealsAppAndroid(appearanceModeManager)
      }
    }
  }
}
