package me.sujanpoudel.playdeals.common.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

actual fun Modifier.withWindowInsetPaddings(): Modifier =
  composed { windowInsetsPadding(WindowInsets.navigationBars.union(WindowInsets.statusBars)) }
