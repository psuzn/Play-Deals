package me.sujanpoudel.playdeals.common.viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

private const val JOB_KEY = "viewModelViewModelCoroutineScope.JOB_KEY"

val ViewModel.viewModelScope: CoroutineScope
  get() {
    val scope: CoroutineScope? = getTag<CloseableCoroutineScope>(JOB_KEY)
    if (scope != null) {
      return scope
    }
    return setTagIfAbsent(JOB_KEY) {
      CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main)
    }
  }

@OptIn(ExperimentalStdlibApi::class)
internal class CloseableCoroutineScope(context: CoroutineContext) : AutoCloseable, CoroutineScope {
  override val coroutineContext: CoroutineContext = context

  override fun close() {
    coroutineContext.cancel()
  }
}
