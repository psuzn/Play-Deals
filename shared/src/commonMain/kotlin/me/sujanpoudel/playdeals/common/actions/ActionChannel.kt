package me.sujanpoudel.playdeals.common.actions

import kotlinx.coroutines.delay
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.ASK_FOR_RATING
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.CHANGELOG
import me.sujanpoudel.playdeals.common.actions.ActionToAttend.NOTIFICATION_PERMISSION
import me.sujanpoudel.playdeals.common.navigation.Navigator

class ActionItemsManager(navigator: Navigator) {

  val actionableItems = ActionToAttend.entries.toMutableList()
  fun nextTick() {

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
}
