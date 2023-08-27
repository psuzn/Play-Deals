package me.sujanpoudel.playdeals.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import me.sujanpoudel.playdeals.common.ui.screens.home.LinkOpener

@Composable
fun PlayDealsAppAndroid() {
  val context = LocalContext.current

  val linkOpener = remember {
    LinkOpener {
      val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
      context.startActivity(browserIntent)
    }
  }

  PlayDealsApp(linkOpener)
}
