package me.sujanpoudel.playdeals.common.utils

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual val isDebugBuild: Boolean
  get() = Platform.isDebugBinary
