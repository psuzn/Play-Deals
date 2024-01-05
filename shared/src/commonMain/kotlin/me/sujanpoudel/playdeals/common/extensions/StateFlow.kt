package me.sujanpoudel.playdeals.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * Combines two [StateFlow]s into a single [StateFlow]
 */
@OptIn(DelicateCoroutinesApi::class)
fun <T1, T2, R> StateFlow<T1>.combineState(
  flow2: StateFlow<T2>,
  scope: CoroutineScope = GlobalScope,
  sharingStarted: SharingStarted = SharingStarted.Eagerly,
  transform: (T1, T2) -> R,
): StateFlow<R> = combine(this, flow2) { o1, o2 -> transform.invoke(o1, o2) }
  .stateIn(scope, sharingStarted, transform.invoke(this.value, flow2.value))
