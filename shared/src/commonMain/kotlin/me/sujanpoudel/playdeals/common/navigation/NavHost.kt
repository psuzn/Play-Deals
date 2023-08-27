package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavHost(navigator: Navigator) {
  val currentEntry by navigator.currentEntry
  val backStackSize by navigator.backStackCount
  val backDispatchConsumer = LocalBackPressConsumer.current

  // we don't need to handle back press if backstack only has one entry
  if (backStackSize > 1) {
    DisposableEffect(Unit) {
      backDispatchConsumer.addListener(navigator::pop)

      onDispose {
        backDispatchConsumer.removeListener(navigator::pop)
      }
    }
  }

  CompositionLocalProvider(
    Navigator.Local provides navigator,
    LocalViewModelFactory provides navigator.viewModelFactory,
  ) {
    // TODO: add a way to customize transition animations
    val transitionSpec: AnimatedContentTransitionScope<NavEntry>.() -> ContentTransform = {
      if (this.initialState.id < this.targetState.id) {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left).togetherWith(
          slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left),
        )
      } else {
        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right).togetherWith(
          slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right),
        )
      }
    }

    currentEntry?.let { entry ->
      AnimatedContent(entry, transitionSpec = transitionSpec) {
        it.destination.content()
      }
    }
  }
}
