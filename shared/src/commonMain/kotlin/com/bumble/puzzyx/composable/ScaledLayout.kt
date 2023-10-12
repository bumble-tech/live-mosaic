package com.bumble.puzzyx.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints.Companion.Infinity
import kotlin.math.roundToInt
import kotlin.math.sqrt

@Composable
internal fun ScaledLayout(
    modifier: Modifier = Modifier,
    content: @Composable @UiComposable () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    maxWidth = constraints.maxWidth,
                    maxHeight = Infinity
                )
            )
        }
        val measuredHeight = placeables.maxOf { it.measuredHeight }
        val maxHeight = constraints.maxHeight.toFloat()
        scale = if (measuredHeight > maxHeight) {
            (measuredHeight / maxHeight) * 1.5f
        } else {
            1f
        }

        layout(
            constraints.maxWidth,
            constraints.maxHeight
        ) { }
    }

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    maxWidth = (constraints.maxWidth * sqrt(scale)).roundToInt(),
                    maxHeight = (constraints.maxHeight * sqrt(scale)).roundToInt()
                )
            )
        }

        layout(
            constraints.maxWidth,
            constraints.maxHeight
        ) {
            placeables.forEach { placeable ->
                placeable.placeRelativeWithLayer(
                    x = 0,
                    y = 0
                ) {
                    scaleX = 1 / sqrt(scale)
                    scaleY = 1 / sqrt(scale)
                    transformOrigin = TransformOrigin(0f, 0f)
                }
            }
        }
    }
}
