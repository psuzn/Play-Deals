package me.sujanpoudel.playdeals.common.extensions

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.drawQuad(
  points: List<Offset>,
  index: Int,
  item: Offset
) {
  val prevX = points[index - 1].x
  val prevY = points[index - 1].y
  val plotX: Float
  val plotY: Float
  if (index == points.size - 1) {
    plotX = item.x
    plotY = item.y
  } else {
    plotX = (prevX + item.x) / 2
    plotY = (prevY + item.y) / 2
  }

  quadraticBezierTo(
    prevX, prevY,
    plotX, plotY
  )
}
