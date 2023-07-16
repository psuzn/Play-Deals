package me.sujanpoudel.playdeals.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateFlowClass<T>(private val delegate: StateFlow<T>) : StateFlow<T> by delegate {
    fun subscribe(block: (T) -> Unit) = GlobalScope.launch(Dispatchers.Main) {
        delegate.collect {
            block(it)
        }
    }
}

fun <T> StateFlow<T>.asStateFlowClass(): StateFlowClass<T> = StateFlowClass(this)