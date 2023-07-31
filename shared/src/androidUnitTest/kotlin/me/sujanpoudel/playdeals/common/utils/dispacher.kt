package me.sujanpoudel.playdeals.common.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
fun setMainDispatcher(dispatcher: CoroutineDispatcher) {
  Dispatchers.setMain(dispatcher)
}
