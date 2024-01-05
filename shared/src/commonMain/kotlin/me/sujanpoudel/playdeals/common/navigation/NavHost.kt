package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder

@Composable
fun NavHost(navGraph: NavGraph) {
  val savableStateHolder = rememberSaveableStateHolder()
  val navigator = remember(navGraph) { Navigator(navGraph, savableStateHolder) }
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
    currentEntry?.also { entry ->
      AnimatedContent(entry, transitionSpec = navigator.transitionSpec) {
        savableStateHolder.SaveableStateProvider(it.id) {
          it.destination.content()
        }
      }
    }
  }
}
