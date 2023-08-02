package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import me.sujanpoudel.playdeals.common.viewModel.ViewModel
import me.sujanpoudel.playdeals.common.viewModel.ViewModelFactory
import me.sujanpoudel.playdeals.common.viewModel.ViewModelStore
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

@OptIn(ExperimentalStdlibApi::class)
@Stable
class Navigator(
  private val navGraph: NavGraph,
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

  fun push(path: PathIdentifier) {
    push(navGraph.getDestination(path))
  }

  private fun push(destination: NavDestination) {
    val entry = NavEntry(lastNavEntryId + 1, destination)
    backStack += entry
    lastNavEntryId = entry.id
    _currentEntry.value = entry
    _backStackDepth.value = backStack.size
  }

  override fun <T : ViewModel> get(clazz: KClass<T>): T? {
    return currentEntry.value?.bagOfTags?.firstNotNullOfOrNull {
      clazz.safeCast(it)
    }
  }

  override fun save(viewModel: ViewModel) {
    currentEntry.value?.addTag(viewModel)
  }
}

val LocalNavigator = compositionLocalOf<Navigator> { throw Error("No Navigator found") }
val LocalViewModelFactory =
  compositionLocalOf<ViewModelFactory> {
    throw Error("No ViewModelFactory found")
  }
