package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickableBoundless(
  enabled: Boolean = true,
  onclick: () -> Unit,
) = clickable(
  interactionSource = remember { MutableInteractionSource() },
  indication = rememberRipple(bounded = false),
  onClick = onclick,
  enabled = enabled,
)

@Composable
fun Modifier.clickableWithoutIndicator(
  enabled: Boolean = true,
  onclick: () -> Unit,
) = clickable(
  interactionSource = remember { MutableInteractionSource() },
  indication = null,
  onClick = onclick,
  enabled = enabled,
)
