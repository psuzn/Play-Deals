package me.sujanpoudel.playdeals.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import me.sujanpoudel.playdeals.common.ui.screens.home.AppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceModeManager

@Composable
fun PlayDealsAppAndroid(appearanceModeManager: AppearanceModeManager) {

  val context = LocalContext.current

  val appDealActionHandler = remember {
    AppDealActionHandler {
      val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.storeUrl))
      context.startActivity(browserIntent)
    }
  }


  PlayDealsApp(
    appearanceModeManager,
    appDealActionHandler
  )
}
