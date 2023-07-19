package me.sujanpoudel.playdeals.common.ui.screens

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

open class TestSuite {

  @OptIn(ExperimentalCoroutinesApi::class)
  protected fun runTest(block: suspend CoroutineScope.() -> Unit): Unit =
    kotlinx.coroutines.test.runTest(UnconfinedTestDispatcher()) {
      block()
    }

  companion object {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun setMainDispatcher(dispatcher: CoroutineDispatcher) {
      Dispatchers.setMain(dispatcher)
    }
  }

}
