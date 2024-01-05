package me.sujanpoudel.playdeals.common.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalStdlibApi::class)
open class ViewModel() : AutoCloseable {
  private val bagOfTags = hashMapOf<String, AutoCloseable>()

  private var isDisposed = false

  open fun onClose() {
  }

  private fun disposeClear() {
    bagOfTags.forEach {
      it.value.close()
    }
  }

  override fun close() {
    disposeClear()
    onClose()
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : AutoCloseable> getTag(key: String): T? = bagOfTags[key] as? T

  @Suppress("UNCHECKED_CAST")
  fun <T : AutoCloseable> setTagIfAbsent(
    key: String,
    valueProducer: () -> T,
  ): T {
    return bagOfTags.getOrPut(key, valueProducer).also {
      if (isDisposed) {
        disposeClear()
      }
    } as T
  }

  sealed interface VMState<T> : StateFlow<T>

  private class VMStateImpl<T> constructor(
    private val delegate: MutableStateFlow<T>,
  ) : MutableStateFlow<T> by delegate, VMState<T>

  fun <T> VMState<T>.update(function: (T) -> T) {
    (this as VMStateImpl<T> as MutableStateFlow<T>).update(function)
  }

  protected fun <T> state(initial: T): VMState<T> = VMStateImpl(MutableStateFlow(initial))
}
