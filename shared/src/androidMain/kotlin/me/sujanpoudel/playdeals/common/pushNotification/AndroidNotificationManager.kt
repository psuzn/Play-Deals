package me.sujanpoudel.playdeals.common.pushNotification

import android.app.NotificationChannel
import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging

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
    getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

  PushNotificationTopic.entries
    .forEach {
      val channel = NotificationChannel(it.identifier, it.label, android.app.NotificationManager.IMPORTANCE_DEFAULT)
      notificationManager.createNotificationChannel(channel)
    }
}
