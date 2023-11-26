package me.sujanpoudel.playdeals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import me.sujanpoudel.playdeals.common.PlayDealsAppAndroid
import me.sujanpoudel.playdeals.common.navigation.BackPressConsumer
import me.sujanpoudel.playdeals.common.navigation.LocalBackPressConsumer

class MainActivity : ComponentActivity() {
  private val callBack = object : OnBackPressedCallback(false) {
    override fun handleOnBackPressed() {
      backPressConsumer.onBackPress()
    }
  }

  private val backPressConsumer: BackPressConsumer =
    BackPressConsumer {
      callBack.isEnabled = it
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    onBackPressedDispatcher.addCallback(callBack)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      CompositionLocalProvider(
        LocalBackPressConsumer provides backPressConsumer,
      ) {
        PlayDealsAppAndroid()
      }
    }
  }
}
