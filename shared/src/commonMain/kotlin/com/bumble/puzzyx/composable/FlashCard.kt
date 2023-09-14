package com.bumble.puzzyx.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.bumble.appyx.interactions.core.ui.property.impl.RotationY
import com.bumble.appyx.interactions.core.ui.property.motionPropertyRenderValue


@Composable
fun FlashCard(
    front: @Composable () -> Unit,
    back: @Composable () -> Unit,
) {
    val rotation = (motionPropertyRenderValue<Float, RotationY>() ?: 0f) % 360f
    if (rotation in 90f..270f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {
            back()
        }
    } else {
        front()
    }
}
