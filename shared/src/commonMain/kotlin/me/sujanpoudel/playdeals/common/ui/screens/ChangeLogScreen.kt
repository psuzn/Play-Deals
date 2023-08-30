package me.sujanpoudel.playdeals.common.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.ui.components.ChangeLog
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.screens.home.LocalLinkOpener
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

class ChangeLog(
  val version: String,
  val date: String,
  val bulletListItems: List<BulletListItem>,
)

class BulletListItem(
  val title: String,
  val description: String,
)

val changeLogs = listOf(
  ChangeLog(
    version = "v1.2.1",
    date = "29th Aug 2023",
    bulletListItems = listOf(
      BulletListItem(
        title = "Play Deal is now Open Source",
        description = "Yes, Play deals is now open source. If you are nerd and " +
          "want to look at the code it is viable at the play deal repo from here",
      ),
    ),
  ),
)

val messages = listOf(
  "Hey This is first paragraph",
  "Hey this is my second paragraph. Any this is 2nd line.",
  "Hey this is 3rd paragraph.",
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChangeLogScreen() {
  val linkOpener = LocalLinkOpener.current

  Scaffold(
    title = "Changelog",
    showNavBackIcon = false,
    actions = {
      val outlineColor = MaterialTheme.colorScheme.outlineVariant

      IconButton(
        onClick = it::pop,
        modifier = Modifier
          .drawBehind {
            drawCircle(outlineColor, radius = size.minDimension / 2.5f)
          },
      ) {
        Icon(
          Icons.Default.Close,
          contentDescription = "Close",
          tint = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier.size(22.dp),
        )
      }
    },
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 16.dp),
    ) {
      var resource by remember { mutableStateOf("") }

      LaunchedEffect(Unit) {
        resource = resource("raw/changelog.json").readBytes().decodeToString()
      }

      Spacer(Modifier.height(24.dp))

      Text("What's New in v1.2.1", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
      Text(
        "15th Feb 2021",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
      )

      Text(resource)

//
//      Text(
//        buildAnnotatedString {
//          messages.forEach {
//            withStyle(style = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))) {
//              append(Typography.bullet)
//              append("\t\t")
//              append(it)
//            }
//          }
//        },
//        modifier = Modifier.padding(top = 16.dp),
//        style = MaterialTheme.typography.bodyMedium
//      )

      ChangeLog.SocialActions(
        modifier = Modifier.padding(top = 24.dp),
      ) {
        linkOpener.openLink(it.url)
      }
    }
  }
}
