package me.sujanpoudel.playdeals.common.pushNotification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import me.sujanpoudel.playdeals.common.utils.isDebugBuild
import kotlin.coroutines.coroutineContext

interface NotificationManager {
  fun subscribeToTopic(topic: String)

  fun unSubscribeFromTopic(topic: String)

  companion object {
    val NONE = object : NotificationManager {
      override fun subscribeToTopic(topic: String) {}

      override fun unSubscribeFromTopic(topic: String) {}
    }
  }
}

val NotificationManager.Companion.current
  @Composable
  @ReadOnlyComposable
  get() = LocalNotificationManager.current

val LocalNotificationManager = compositionLocalOf {
  NotificationManager.NONE
}

suspend fun NotificationManager.syncNotificationTopics(appPreferences: AppPreferences) {
  listOf(
    appPreferences.developerMode to PushNotificationTopic.MAINTENANCE_LOG,
    appPreferences.subscribeToFreeDeals to PushNotificationTopic.FREE_DEAL,
    appPreferences.subscribeToDiscountDeals to PushNotificationTopic.DISCOUNT_DEAL,
    appPreferences.subscribeDealSummary to PushNotificationTopic.DEALS_SUMMARY,
  ).forEach { (subscriptionFlow, topic) ->

    CoroutineScope(coroutineContext).launch {
      subscriptionFlow.collectLatest { subscribed ->
        if (subscribed) {
          subscribeToTopic(topic.identifier)
        } else {
          unSubscribeFromTopic(topic.identifier)
        }

        if (isDebugBuild) {
          if (subscribed) {
            subscribeToTopic("${topic.identifier}-dev")
          } else {
            unSubscribeFromTopic("${topic.identifier}-dev")
          }
        }
      }
    }
  }
}
