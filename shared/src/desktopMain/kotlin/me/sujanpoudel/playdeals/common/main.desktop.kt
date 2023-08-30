package me.sujanpoudel.playdeals.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import me.sujanpoudel.playdeals.common.ui.screens.home.LinkOpener
import java.awt.Desktop
import java.net.URI

fun String.asURI() = URI(replace(" ", "+"))

@Composable
fun MainDesktopView() {
  val linkOpener = remember {
    LinkOpener {
      Desktop.getDesktop().browse(it.asURI())
    }
  }
  PlayDealsApp(linkOpener)
}
