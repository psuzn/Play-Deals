package me.sujanpoudel.playdeals.common.utils.settings

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import kotlin.reflect.KClass

private class PseudoObservableSettings(
  private val delegate: Settings = Settings(),
) : ObservableSettings, Settings by delegate {

  private val listenerMap = mutableMapOf<String, MutableSet<Listener<*>>>()

  private inline fun <reified T> createAndRegisterListener(key: String, noinline callback: (T) -> Unit): Listener<T> {
    return Listener(key, T::class, callback).also {
      listenerMap.getOrPut(key) { mutableSetOf() }
        .apply {
          add(it)
        }
    }
  }

  override fun addBooleanListener(key: String, defaultValue: Boolean, callback: (Boolean) -> Unit) =
    createAndRegisterListener<Boolean>(key, callback)

  override fun addBooleanOrNullListener(key: String, callback: (Boolean?) -> Unit) =
    createAndRegisterListener<Boolean?>(key, callback)

  override fun addDoubleListener(key: String, defaultValue: Double, callback: (Double) -> Unit) =
    createAndRegisterListener<Double>(key, callback)

  override fun addDoubleOrNullListener(key: String, callback: (Double?) -> Unit) =
    createAndRegisterListener<Double?>(key, callback)

  override fun addFloatListener(key: String, defaultValue: Float, callback: (Float) -> Unit) =
    createAndRegisterListener<Float>(key, callback)

  override fun addFloatOrNullListener(key: String, callback: (Float?) -> Unit) =
    createAndRegisterListener<Float?>(key, callback)

  override fun addIntListener(key: String, defaultValue: Int, callback: (Int) -> Unit) =
    createAndRegisterListener<Int>(key, callback)

  override fun addIntOrNullListener(key: String, callback: (Int?) -> Unit) =
    createAndRegisterListener<Int?>(key, callback)

  override fun addLongListener(key: String, defaultValue: Long, callback: (Long) -> Unit) =
    createAndRegisterListener<Long>(key, callback)

  override fun addLongOrNullListener(key: String, callback: (Long?) -> Unit) =
    createAndRegisterListener<Long?>(key, callback)

  override fun addStringListener(key: String, defaultValue: String, callback: (String) -> Unit) =
    createAndRegisterListener<String>(key, callback)

  override fun addStringOrNullListener(key: String, callback: (String?) -> Unit) =
    createAndRegisterListener<String?>(key, callback)

  override fun putString(key: String, value: String) {
    delegate.putString(key, value)
    invokeListeners(key, value)
  }

  override fun putBoolean(key: String, value: Boolean) {
    delegate.putBoolean(key, value)
    invokeListeners(key, value)
  }

  fun removeListener(key: String, listener: Listener<*>) {
    listenerMap[key]?.apply {
      remove(listener)
      if (isEmpty()) {
        listenerMap.remove(key)
      }
    }
  }

  @Suppress("UNCHECKED_CAST")
  private inline fun <reified T> invokeListeners(key: String, value: T) {
    listenerMap[key]?.forEach {
      if (it.type.isInstance(value)) {
        (it as Listener<T>).callback(value)
      }
    }
  }

  open inner class Listener<T>(
    private val key: String,
    val type: KClass<*>,
    val callback: (T) -> Unit,
  ) : SettingsListener {

    override fun deactivate() = removeListener(key, this)
  }
}

fun Settings.asObservableSettings(): ObservableSettings = PseudoObservableSettings(this)
