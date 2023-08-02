package me.sujanpoudel.playdeals.common.viewModel

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
}
