package me.sujanpoudel.playdeals.common.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.sujanpoudel.playdeals.common.BuildKonfig
import me.sujanpoudel.playdeals.common.extensions.capitalizeWords
import me.sujanpoudel.playdeals.common.strings.AppLanguage
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.screens.settings.icon
import me.sujanpoudel.playdeals.common.ui.theme.AppearanceMode
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object SettingsScreen {

  @Composable
  fun SettingItem(
    title: String,
    description: String,
    onClick: () -> Unit,
    rightAction: @Composable BoxScope.() -> Unit = {},
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
      Column(
        modifier = Modifier.weight(1f),
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
        )
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onBackground.copy(SOFT_COLOR_ALPHA),
        )
      }

      Box(
        modifier = Modifier.align(Alignment.CenterVertically),
      ) {
        rightAction()
      }
    }
  }

  @Composable
  fun Footer(
    onClick: () -> Unit,
    color: Color,
  ) {
    Column(
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(Strings.appDeals, style = MaterialTheme.typography.titleSmall, color = color)
      Text(
        "V${BuildKonfig.VERSION_NAME}-${BuildKonfig.VERSION_CODE}",
        style = MaterialTheme.typography.bodySmall,
        color = color,
      )
    }
  }

  @OptIn(ExperimentalStdlibApi::class)
  @Composable
  fun AppearanceModeSetting(
    appearanceMode: AppearanceMode,
    setAppearanceMode: (AppearanceMode) -> Unit,
  ) {
    var dialogActive by remember { mutableStateOf(false) }

    SettingItem(
      title = Strings.appearance,
      description = Strings.appearanceModeDescription,
      onClick = { dialogActive = true },
    ) {
      Icon(appearanceMode.icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
    }

    ChoiceDialogue(
      title = Strings.chooseLanguage,
      choices = remember { AppearanceMode.values().toList() },
      isActive = dialogActive,
      closeRequest = { dialogActive = false },
      onChooseValue = setAppearanceMode,
      choiceLabelBy = { it.name.lowercase().capitalizeWords() },
      choiceIcon = {
        Icon(it.icon, contentDescription = it.name, tint = MaterialTheme.colorScheme.primary)
      },
    )
  }

  @Composable
  fun LanguageSetting(
    language: AppLanguage,
    setLanguage: (AppLanguage) -> Unit,
  ) {
    var dialogActive by remember { mutableStateOf(false) }

    SettingItem(
      title = Strings.chooseLanguage,
      description = Strings.appearanceModeDescription,
      onClick = { dialogActive = true },
    ) {
      Text(language.flag, style = MaterialTheme.typography.bodyLarge)
    }

    ChoiceDialogue(
      title = Strings.chooseLanguage,
      choices = remember { AppLanguage.values().toList() },
      isActive = dialogActive,
      closeRequest = { dialogActive = false },
      onChooseValue = setLanguage,
      choiceLabelBy = { it.label },
      choiceIcon = {
        Text(it.flag, style = MaterialTheme.typography.bodyLarge)
      },
    )
  }

  @Composable
  fun <T> ChoiceDialogue(
    title: String,
    choices: List<T>,
    isActive: Boolean,
    closeRequest: () -> Unit,
    onChooseValue: (T) -> Unit,
    choiceLabelBy: (T) -> String = { it.toString() },
    choiceIcon: @Composable (T) -> Unit = { },
  ) {
    if (isActive) {
      Dialog(closeRequest, properties = DialogProperties()) {
        Column(
          modifier = Modifier
            .clip(RoundedCornerShape(5))
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 16.dp),
        ) {
          Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
              .align(Alignment.CenterHorizontally),
          )

          Divider()

          choices.forEach {
            Row(
              modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clickable {
                  closeRequest()
                  onChooseValue(it)
                }
                .padding(horizontal = 16.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Text(
                text = choiceLabelBy(it),
                modifier = Modifier.weight(1f).apply {
                  if (choices.last() == it) {
                    this.padding(bottom = 32.dp)
                  }
                },
                style = MaterialTheme.typography.titleSmall,
              )

              choiceIcon(it)
            }

            if (choices.last() != it) {
              Divider()
            }
          }
        }
      }
    }
  }
}
