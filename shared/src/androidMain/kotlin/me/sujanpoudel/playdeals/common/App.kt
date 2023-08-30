package me.sujanpoudel.playdeals.common

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import me.sujanpoudel.playdeals.common.ui.screens.home.LinkOpener
import java.io.File

@Composable
fun PlayDealsAppAndroid() {
  val context = LocalContext.current

  LaunchedEffect(Unit) {
    val b = context.assets

//    Font(fileDescriptor = InputStream().readBytes() )

    val file = File("font/roboto_bold.ttf")
    println(file)
  }

  val linkOpener = remember {
    LinkOpener {
      val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
      context.startActivity(browserIntent)
    }
  }

  PlayDealsApp(linkOpener)
}
