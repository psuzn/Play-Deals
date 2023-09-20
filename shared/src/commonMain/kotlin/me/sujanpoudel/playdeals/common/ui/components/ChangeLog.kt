package me.sujanpoudel.playdeals.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.clickableBoundless
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class SocialAction(
  val iconRes: String,
  val url: String,
) {
  GITHUB("drawable/ic_github.png", "https://github.com/psuzn"),
  WEB("drawable/ic_web.png", "https://sujanpoudel.me"),
  LINKEDIN("drawable/ic_linkedin.png", "https://www.linkedin.com/in/psuzn/"),
  TWITTER("drawable/ic_twitter.png", "https://twitter.com/psuzn/"),
  MAIL("drawable/ic_gmail.png", "mailto:mail@sujanpoudel.me?subject=Feedback For Play Deals(v)"),
  INSTAGRAM("drawable/ic_instagram.png", "https://instagram.com/psuzn_"),
}

object ChangeLog {

  @OptIn(ExperimentalResourceApi::class)
  @Composable
  fun SocialActions(
    modifier: Modifier = Modifier,
    onClick: (SocialAction) -> Unit,
  ) {
    val actions = remember { SocialAction.values().toList() }

    Column(
      modifier = modifier,
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        Strings.findMeOn,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
      )

      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        actions.forEach {
          Icon(
            painterResource(it.iconRes),
            it.name,
            modifier = Modifier
              .clickableBoundless { onClick.invoke(it) }
              .padding(all = 4.dp)
              .size(18.dp),
            tint = MaterialTheme.colorScheme.onBackground,
          )
        }
      }
    }
  }
}
