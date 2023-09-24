package me.sujanpoudel.playdeals.common.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.markdownColor
import com.mikepenz.markdown.model.markdownTypography
import me.sujanpoudel.playdeals.common.BuildKonfig
import me.sujanpoudel.playdeals.common.di.PrimaryDI
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.ChangeLog
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import org.kodein.di.direct
import org.kodein.di.instance

private const val SEPARATOR = "-------------------------------------------"
private const val CHANGELOG_PATH = "raw/changelog.md"

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChangeLogScreen() = Scaffold(
  title = Strings.changelog,
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
        contentDescription = Strings.close,
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.size(22.dp),
      )
    }
  },
) {
  val appPreferences = remember { PrimaryDI.direct.instance<AppPreferences>() }

  var isChangelogExpanded by remember { mutableStateOf(false) }

  var previousChangelogs by remember { mutableStateOf<List<String>>(emptyList()) }
  var currentChangelog by remember { mutableStateOf<String?>(null) }
  val linkHandler = LocalUriHandler.current

  LaunchedEffect(Unit) {
    appPreferences.setChangelogShownVersion(BuildKonfig.VERSION_CODE)
  }

  LaunchedEffect(CHANGELOG_PATH) {
    val changelogs = resource(CHANGELOG_PATH).readBytes().decodeToString().split(SEPARATOR)
      .filter { it.isNotEmpty() }

    currentChangelog = changelogs.first()
    previousChangelogs = changelogs.drop(1)
  }

  if (currentChangelog != null) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp),
      contentPadding = PaddingValues(top = 24.dp, bottom = 16.dp),
    ) {
      currentChangelog?.also {
        item {
          ChangeLogItem(it)
        }
      }

      if (isChangelogExpanded && previousChangelogs.isNotEmpty()) {
        items(previousChangelogs) {
          ChangeLogItem(
            string = it,
            modifier = Modifier.animateItemPlacement(),
          )
        }
      }

      if (previousChangelogs.isNotEmpty()) {
        item("button") {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .animateItemPlacement(),
            contentAlignment = Alignment.Center,
          ) {
            TextButton(
              onClick = { isChangelogExpanded = !isChangelogExpanded },
            ) {
              Text(if (isChangelogExpanded) Strings.close else Strings.oldChangeLog)
            }
          }
        }
      }

      item("social-actions") {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(),
          contentAlignment = Alignment.Center,
        ) {
          ChangeLog.SocialActions(
            modifier = Modifier
              .padding(horizontal = 16.dp, vertical = 16.dp)
              .align(Alignment.BottomCenter),
          ) {
            linkHandler.openUri(it.url)
          }
        }
      }
    }
  }
}

@Composable
fun ChangeLogItem(
  string: String,
  modifier: Modifier = Modifier,
) = Markdown(
  modifier = modifier.fillMaxWidth(),
  content = string,
  colors = markdownColor(MaterialTheme.colorScheme.onBackground),
  typography = markdownTypography(
    h1 = MaterialTheme.typography.titleMedium.copy(
      fontWeight = FontWeight.Medium,
    ),
    h2 = MaterialTheme.typography.titleMedium,
    paragraph = MaterialTheme.typography.bodySmall.copy(
      color = MaterialTheme.colorScheme.onBackground.copy(0.6f),
    ),
    bullet = MaterialTheme.typography.bodyMedium,
  ),
)
