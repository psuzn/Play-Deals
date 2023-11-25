package me.sujanpoudel.playdeals.common.pushNotification

import android.app.NotificationChannel
import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class AndroidNotificationManager(context: Context) : NotificationManager {

  init {
    context.registerNotificationChannels()
  }

  override fun subscribeToTopic(topic: String) {
    FirebaseMessaging.getInstance().subscribeToTopic(topic)
  }

  override fun unSubscribeFromTopic(topic: String) {
    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
  }
}

fun Context.registerNotificationChannels() {
  val notificationManager =
    getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as android.app.NotificationManager

  PushNotificationTopic.entries
    .forEach {
      val channel = NotificationChannel(it.identifier, it.label, android.app.NotificationManager.IMPORTANCE_DEFAULT)
      notificationManager.createNotificationChannel(channel)
    }
}
