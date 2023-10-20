package me.sujanpoudel.playdeals.common.actions

import kotlinx.coroutines.delay
import me.sujanpoudel.playdeals.common.BuildKonfig
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.ASK_FOR_RATING
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.CHANGELOG
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.NOTIFICATION_PERMISSION
import me.sujanpoudel.playdeals.common.di.PrimaryDI
import me.sujanpoudel.playdeals.common.domain.persistent.AppPreferences
import org.kodein.di.direct
import org.kodein.di.instance


enum class ActionToAttend {
  CHANGELOG,
  NOTIFICATION_PERMISSION,
  ASK_FOR_RATING
}


internal suspend fun processActionableItems() {

  val actionableItems = ActionToAttend.entries.toMutableList()

  PrimaryDI.direct.instance<AppPreferences>()

  while (actionableItems.isNotEmpty()) {
    val item = actionableItems.first()
    if (isActionNecessary(item)) {
      performAction(item)
    }
    actionableItems -= item
  }
}


private fun AppPreferences.checkIfChangelogNeedsToBeShown(): Boolean {
  return BuildKonfig.MAJOR_RELEASE && getChangelogShownVersion() != BuildKonfig.VERSION_CODE
}

fun isActionNecessary(actionToAttend: ActionToAttend): Boolean {

  val appPreferences = PrimaryDI.direct.instance<AppPreferences>()

  return when (actionToAttend) {
    CHANGELOG -> appPreferences.checkIfChangelogNeedsToBeShown()
    ASK_FOR_RATING -> false
    NOTIFICATION_PERMISSION -> false
  }
}


private suspend fun performAction(actionToAttend: ActionToAttend) {
  when (actionToAttend) {
    CHANGELOG -> {
      delay(3000)
    }

    NOTIFICATION_PERMISSION -> TODO()
    ASK_FOR_RATING -> TODO()
  }
}
