package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.runtime.Composable

typealias PathIdentifier = Enum<*>
typealias Content = @Composable () -> Unit

data class NavDestination(val path: PathIdentifier, val content: Content)

interface NavGraphBuilder {
  fun destination(
    path: PathIdentifier,
    content: Content,
  )

  var homePath: PathIdentifier
}

class NavGraph(buildBy: NavGraphBuilder.() -> Unit) {
  private val destinations: MutableMap<PathIdentifier, NavDestination>
  private val startingPath: PathIdentifier

  init {
    val builder = NavGraphBuilderImpl().apply(buildBy)
    destinations = builder.buildDestination()
    startingPath = builder.homePath
  }

  fun getDestination(identifier: PathIdentifier): NavDestination {
    return destinations[identifier]
      ?: throw Error("No destination found for '$identifier'")
  }

  fun getStartingDestination(): NavDestination {
    return getDestination(startingPath)
  }

  private class NavGraphBuilderImpl : NavGraphBuilder {
    private val destinations: MutableMap<PathIdentifier, NavDestination> = mutableMapOf()

    override fun destination(
      path: PathIdentifier,
      content: Content,
    ) {
      destinations[path] = NavDestination(path, content)
    }

    override lateinit var homePath: PathIdentifier

    fun buildDestination(): MutableMap<PathIdentifier, NavDestination> {
      if (!this::homePath.isInitialized) {
        throw Error("homePath is missing")
      }

      return destinations
    }
  }
}
