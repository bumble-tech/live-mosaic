package com.bumble.livemosaic.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

@Composable
internal fun OptimisingLayout(
    optimalWidth: Dp,
    modifier: Modifier = Modifier,
    paddingFraction: Float = 0.12f,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable @UiComposable () -> Unit
) {
    Box(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize((1f - paddingFraction).coerceIn(0f, 1f))
                .align(contentAlignment)
        ) {
            ScaledLayout(
                scale = (this@BoxWithConstraints.maxWidth / optimalWidth).coerceAtMost(1f),
                content = content
            )
        }
    }
}


@Composable
private fun ScaledLayout(
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
