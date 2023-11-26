package me.sujanpoudel.playdeals.common.pushNotification

enum class PushNotificationTopic(val identifier: String, val label: String) {
  DISCOUNT_DEAL("discount-deal", "Discount Deal"),
  FREE_DEAL("free-deal", "Free Deal"),
  DEALS_SUMMARY("deals-summary", "Deal Summary"),
  MAINTENANCE_LOG("dev-log", "Log"),
}
