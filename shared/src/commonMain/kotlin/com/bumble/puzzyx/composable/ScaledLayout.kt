package com.bumble.puzzyx.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import kotlin.math.roundToInt

@Composable
internal fun ScaledLayout(
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    content: @Composable @UiComposable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val scaledUpConstraints = constraints.copy(
            maxWidth = (constraints.maxWidth / scale).roundToInt(),
            maxHeight = (constraints.maxHeight / scale).roundToInt()
        )
        val placeables = measurables.map { measurable ->
            measurable.measure(scaledUpConstraints)
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
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin(0f, 0f)
                }
            }
        }
    }
}
