package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaveableStateHolder
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.ViewModelFactory
import me.sujanpoudel.playdeals.common.viewModel.ViewModelStore
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

@OptIn(ExperimentalStdlibApi::class)
@Stable
class Navigator(
  private val navGraph: NavGraph,
  private val savableStateHolder: SaveableStateHolder,
) : ViewModelStore {
  val viewModelFactory by lazy { ViewModelFactory(this) }

  private var backStack: List<NavEntry> = emptyList()

  private val _currentEntry = mutableStateOf<NavEntry?>(null)
  val currentEntry: State<NavEntry?> = _currentEntry

  private val _backStackDepth = mutableStateOf(0)
  val backStackCount: State<Int> = _backStackDepth

  private var lastNavEntryId: Long = 0

  init {
    push(navGraph.getStartingDestination())
  }

  fun pop(): Boolean {
    if (backStack.size == 1) {
      return false; // nothing to pop
    }

    val entry = backStack.last()
    backStack -= entry
    _currentEntry.value = backStack.last()
    _backStackDepth.value = backStack.size
    entry.destroy()

    return true
  }

  fun push(
    path: PathIdentifier,
    enterTransition: NavEnterTransition = NavTransitions.slideInFromRight,
    exitTransition: NavExitTransition = NavTransitions.slideOutToLeft,
    popEnter: NavEnterTransition = NavTransitions.slideInFromLeft,
    popExit: NavExitTransition = NavTransitions.slideOutToRight,
  ) {
    push(navGraph.getDestination(path), enterTransition, exitTransition, popEnter, popExit)
  }

  private fun push(
    destination: NavDestination,
    enterTransition: NavEnterTransition = NavTransitions.slideInFromRight,
    exitTransition: NavExitTransition = NavTransitions.slideOutToLeft,
    popEnter: NavEnterTransition = NavTransitions.slideInFromLeft,
    popExit: NavExitTransition = NavTransitions.slideOutToRight,
  ) {
    val entry = NavEntry(lastNavEntryId + 1, destination, enterTransition, exitTransition, popEnter, popExit)
    backStack += entry
    lastNavEntryId = entry.id
    _currentEntry.value = entry
    _backStackDepth.value = backStack.size

    entry.addTag {
      savableStateHolder.removeState(entry.id)
    }
  }

  override fun <T : ViewModel> get(clazz: KClass<T>): T? {
    return currentEntry.value?.bagOfTags?.firstNotNullOfOrNull {
      clazz.safeCast(it)
    }
  }

  override fun save(viewModel: ViewModel) {
    currentEntry.value?.addTag(viewModel)
  }

  companion object {
    internal val Local = compositionLocalOf<Navigator> { throw Error("No Navigator found") }
    val current
      @ReadOnlyComposable
      @Composable get() = Local.current
  }
}

val LocalViewModelFactory = compositionLocalOf<ViewModelFactory> {
  throw Error("No ViewModelFactory found")
}

typealias NavEnterTransition = AnimatedContentTransitionScope<*>.() -> EnterTransition
typealias NavExitTransition = AnimatedContentTransitionScope<*>.() -> ExitTransition

object NavTransitions {
  val slideInFromRight: NavEnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
  }
  val slideInFromLeft: NavEnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
  }
  val slideInFromBottom: NavEnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
  }
  val slideInFromTop: NavEnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down)
  }

  val EmptyEnterTransition: NavEnterTransition = {
    EnterTransition.None
  }

  val slideOutToRight: NavExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
  }
  val slideOutToLeft: NavExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
  }
  val slideOutToBottom: NavExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
  }
  val slideOutToTop: NavExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up)
  }

  val EmptyExitTransition: NavExitTransition = {
    ExitTransition.None
  }
}
