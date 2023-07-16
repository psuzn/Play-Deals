package me.sujanpoudel.playdeals.common.navigation

import androidx.compose.runtime.compositionLocalOf


val LocalBackPressConsumer = compositionLocalOf {
    BackPressConsumer {}
}

class BackPressConsumer(
    private val activateBy: (Boolean) -> Unit
) {
    private var listeners = mutableSetOf<() -> Unit>()

    fun onBackPress() {
        listeners.forEach { it.invoke() }
    }

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
        activateBy.invoke(true) // we have listener so active itself
    }

    fun removeListener(listener: () -> Unit) {
        listeners.remove(listener)
        activateBy.invoke(listeners.isNotEmpty()) // we might not have active listeners
    }
}

