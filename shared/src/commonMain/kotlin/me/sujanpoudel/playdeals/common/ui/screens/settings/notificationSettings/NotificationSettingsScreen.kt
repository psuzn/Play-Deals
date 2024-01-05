package me.sujanpoudel.playdeals.common.ui.screens.settings.notificationSettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.PermissionManager
import me.sujanpoudel.playdeals.common.PermissionStatus
import me.sujanpoudel.playdeals.common.pushNotification.pushNotificationPermissionManager
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.Scaffold
import me.sujanpoudel.playdeals.common.ui.components.common.rememberTextTitle
import me.sujanpoudel.playdeals.common.ui.components.settings.SettingsScreen.SettingItem
import me.sujanpoudel.playdeals.common.viewModel.viewModel

@Composable
fun NotificationSettingsScreen() {
  val viewModel = viewModel<NotificationSettingsScreenViewModel>()
  val notificationPermissionManager = PermissionManager.pushNotificationPermissionManager

  val isFreeDealNotificationEnabled by viewModel.freeDealNotification.collectAsState()
  val isDiscountNotificationEnabled by viewModel.nonFreeDealNotification.collectAsState()
  val isSummaryNotificationEnabled by viewModel.summaryNotification.collectAsState()
  val isDeveloperModeEnabled by viewModel.developerMode.collectAsState()

  val isAllDealNotificationEnabled by derivedStateOf { isFreeDealNotificationEnabled && isDiscountNotificationEnabled }
  val isNotificationEnabled by derivedStateOf {
    isFreeDealNotificationEnabled || isDiscountNotificationEnabled ||
      isSummaryNotificationEnabled || isDeveloperModeEnabled
  }

  val permissionStatus by notificationPermissionManager.permissionState
  val permissionGranted by derivedStateOf { permissionStatus == PermissionStatus.Granted }

  Scaffold(title = rememberTextTitle(Strings.pushNotification)) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      SettingItem(
        title = Strings.subscribeToAllNewDeals,
        description = Strings.subscribeToAllNewDealsDescription,
        onClick = { viewModel.subscribeToAllDeals(isAllDealNotificationEnabled.not()) },
        rightAction = {
          Switch(
            checked = isAllDealNotificationEnabled,
            onCheckedChange = {
              viewModel.subscribeToAllDeals(isAllDealNotificationEnabled.not())
            },
          )
        },
      )

      Divider()

      SettingItem(
        title = Strings.subscribeToFreeDeals,
        description = Strings.subscribeToFreeDealsDescription,
        onClick = { viewModel.subscribeToFreeDeals(isFreeDealNotificationEnabled.not()) },
        rightAction = {
          Switch(
            checked = isFreeDealNotificationEnabled,
            onCheckedChange = {
              viewModel.subscribeToFreeDeals(isFreeDealNotificationEnabled.not())
            },
          )
        },
      )

      SettingItem(
        title = Strings.subscribeToDiscountFreeDeal,
        description = Strings.subscribeToDiscountDealDescription,
        onClick = { viewModel.subscribeDiscountDeals(isDiscountNotificationEnabled.not()) },
        rightAction = {
          Switch(
            checked = isDiscountNotificationEnabled,
            onCheckedChange = {
              viewModel.subscribeDiscountDeals(isDiscountNotificationEnabled.not())
            },
          )
        },
      )

      Divider()

      SettingItem(
        title = Strings.subscribeToSummary,
        description = Strings.subscribeToSummaryDescription,
        onClick = { viewModel.subscribeToSummary(isSummaryNotificationEnabled.not()) },
        rightAction = {
          Switch(
            checked = isSummaryNotificationEnabled,
            onCheckedChange = {
              viewModel.subscribeToSummary(isSummaryNotificationEnabled.not())
            },
          )
        },
      )

      if (viewModel.wasDeveloperModeEnabled) {
        SettingItem(
          title = Strings.developerMode,
          description = Strings.developerModeDescription,
          onClick = { viewModel.setDeveloperMode(isDeveloperModeEnabled.not()) },
          rightAction = {
            Switch(
              checked = isDeveloperModeEnabled,
              onCheckedChange = {
                viewModel.setDeveloperMode(isDeveloperModeEnabled.not())
              },
            )
          },
        )
      }

      Spacer(
        modifier = Modifier.weight(1f),
      )

      if (!permissionGranted && isNotificationEnabled) {
        Card(
          modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        ) {
          Text(
            Strings.grantPermission,
            modifier = Modifier.fillMaxWidth()
              .padding(top = 16.dp)
              .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
          )

          TextButton(
            modifier = Modifier
              .padding(top = 8.dp)
              .align(Alignment.CenterHorizontally),
            onClick = {
              if (permissionStatus == PermissionStatus.Denied) {
                notificationPermissionManager.showRationale()
              } else if (permissionStatus == PermissionStatus.NotAsked) {
                notificationPermissionManager.askForPermission()
              }
            },
          ) {
            Text(Strings.grantPermission)
          }
        }
      }
    }
  }
}
