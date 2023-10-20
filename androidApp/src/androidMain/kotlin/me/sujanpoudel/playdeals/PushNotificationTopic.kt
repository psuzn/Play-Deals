package me.sujanpoudel.playdeals

import android.app.NotificationChannel
import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessaging


enum class PushNotificationTopic(val identifier: String, val ncLabel: String) {
  NEW_DISCOUNT_DEAL("new-discount-deal", "New Discount Deal"),
  NEW_FREE_DEAL("new-free-deal", "New Free Deal"),
  DEALS_SUMMARY("deals-summary", "Deals Summary")
}


fun PushNotificationTopic.notificationChannel() =
  NotificationChannel(identifier, ncLabel, NotificationManager.IMPORTANCE_DEFAULT)

fun FirebaseMessaging.subscribeToTopic(topic: PushNotificationTopic) {
  subscribeToTopic(topic.identifier)
}

fun FirebaseMessaging.unSubscribeFromTopic(topic: PushNotificationTopic) {
  unsubscribeFromTopic(topic.identifier)
}
