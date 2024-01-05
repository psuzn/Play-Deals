package me.sujanpoudel.playdeals.common.utils.settings

import com.russhwolf.settings.ObservableSettings
import kotlinx.coroutines.flow.StateFlow
import me.sujanpoudel.playdeals.common.utils.mapState

class SettingState<T>(
  private val delegateFlow: StateFlow<T>,
  private val updatePreference: (T) -> Unit,
) : StateFlow<T> by delegateFlow {
  fun update(newValue: T) = updatePreference(newValue)
}

private fun <T> createSettingState(
  key: String,
  defaultValue: T,
  createStateFlow: (String, T) -> StateFlow<T>,
  updatePreference: (String, T) -> Unit,
) = SettingState(
  delegateFlow = createStateFlow(key, defaultValue),
  updatePreference = {
    updatePreference(key, it)
  },
)

private fun <T> createNullableSettingState(
  key: String,
  createStateFlow: (String) -> StateFlow<T?>,
  updatePreference: (String, T?) -> Unit,
) = SettingState(
  delegateFlow = createStateFlow(key),
  updatePreference = {
    updatePreference(key, it)
  },
)

fun ObservableSettings.boolSettingState(key: String, defaultValue: Boolean) =
  createSettingState(key, defaultValue, ::boolAsFlow, ::putBoolean)

fun ObservableSettings.stringSettingState(key: String, defaultValue: String) =
  createSettingState(key, defaultValue, ::stringAsFlow, ::putString)

fun <T> ObservableSettings.stringBackedSettingState(
  key: String,
  defaultValue: T,
  toString: (T) -> String = { it.toString() },
  fromString: (String) -> T,
): SettingState<T> = createSettingState(
  key,
  defaultValue,
  createStateFlow = { _, _ ->
    stringAsFlow(key, toString(defaultValue))
      .mapState { fromString(it) }
  },
  updatePreference = { _, value ->
    putString(key, toString(value))
  },
)

fun <T> ObservableSettings.nullableStringBackedSettingState(
  key: String,
  toString: (T) -> String = { it.toString() },
  fromString: (String) -> T,
): SettingState<T?> = createNullableSettingState(
  key = key,
  createStateFlow = { _ ->
    nullableStringAsFlow(key)
      .mapState { it?.let(fromString) }
  },
  updatePreference = { _, value ->
    if (value == null) {
      remove(key)
    } else {
      putString(key, toString(value))
    }
  },
)
