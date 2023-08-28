package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

/**
 *  Nav entry can save the things limited to it's own lifecycle eg: ViewModel
 */
@OptIn(ExperimentalStdlibApi::class)
data class NavEntry(
  val id: Long,
  val destination: NavDestination,
  val enter: AnimatedContentTransitionScope<NavEntry>.() -> EnterTransition,
  val exitTransition: AnimatedContentTransitionScope<NavEntry>.() -> ExitTransition,
  val popEnter: AnimatedContentTransitionScope<NavEntry>.() -> EnterTransition,
  val popExit: AnimatedContentTransitionScope<NavEntry>.() -> ExitTransition,
) {
  @OptIn(ExperimentalStdlibApi::class)
  val bagOfTags = mutableSetOf<AutoCloseable>()

  fun addTag(closable: AutoCloseable) {
    bagOfTags.add(closable)
  }

  fun destroy() {
    bagOfTags.forEach {
      it.close()
    }
    bagOfTags.clear()
  }
}

interface Transition {
  val enter: EnterTransition
  val exit: EnterTransition
  val popEnter: EnterTransition
  val popExit: ExitTransition
}
