package me.sujanpoudel.playdeals.common.utils.settings

import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private fun <T> createStateFlow(
  key: String,
  defaultValue: T,
  getValue: (String, T) -> T,
  addListener: (String, T, (T) -> Unit) -> Unit,
): StateFlow<T> = MutableStateFlow(getValue(key, defaultValue)).apply {
  addListener(key, defaultValue) {
    tryEmit(it)
  }
}

fun ObservableSettings.boolAsFlow(key: String, defaultValue: Boolean) =
  createStateFlow(key, defaultValue, ::getBoolean, ::addBooleanListener)

fun ObservableSettings.stringAsFlow(key: String, defaultValue: String): StateFlow<String> =
  createStateFlow(key, defaultValue, ::getString, ::addStringListener)
