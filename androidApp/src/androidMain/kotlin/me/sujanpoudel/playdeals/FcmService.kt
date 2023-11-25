package me.sujanpoudel.playdeals

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import com.google.firebase.messaging.CommonNotificationBuilder
import com.google.firebase.messaging.Constants.MessageNotificationKeys
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.ImageDownload
import com.google.firebase.messaging.NotificationParams
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmService : FirebaseMessagingService() {

  override fun onMessageReceived(message: RemoteMessage) {
    val data = message.toIntent().extras

    if (!NotificationParams.isNotification(data)) {
      return super.onMessageReceived(message)
    }

    showNotification(NotificationParams(data!!))
  }
}

private fun Context.manifestMetadata() = try {
  applicationContext.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    .metaData
} catch (e: PackageManager.NameNotFoundException) {
  null
}

@SuppressLint("VisibleForTests")
private fun Context.showNotification(notificationParams: NotificationParams) {
  val manifestMetadata = manifestMetadata()
  val channel = CommonNotificationBuilder.getOrCreateChannel(
    this,
    notificationParams.notificationChannelId,
    manifestMetadata,
  )

  val info = CommonNotificationBuilder.createNotificationInfo(
    this,
    this,
    notificationParams,
    channel,
    manifestMetadata,
  )

  val url = notificationParams.getString(MessageNotificationKeys.IMAGE_URL)

  CoroutineScope(Dispatchers.IO).launch {
    if (url != null) {
      val image = withTimeoutOrNull(10.seconds) {
        try {
          ImageDownload.create(url)?.blockingDownload()
        } catch (_: Exception) {
          null
        }
      }
      info.notificationBuilder.setLargeIcon(image)
    }

    val notificationManager = getSystemService(FirebaseMessagingService.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(info.tag, info.id, info.notificationBuilder.build())
  }
}
