package com.bumble.livemosaic.participant

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage
import kotlin.math.max

private const val STARTING_GRADIENT_OFFSET = 0f
private const val MIDDLE_GRADIENT_OFFSET = 70f

@Composable
fun SteweMetalCustomComposable() {

    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing,
            ),
        )
    )

    EmbeddableResourceImage(
        path = "android_carved"
    )

    Text(
        text = "Spooky DcLondon",
        modifier = Modifier
            .rotatingGradientFill(
                rotation = rotation,
                brush = Brush.linearGradient(
                    STARTING_GRADIENT_OFFSET to Color(0xFF3EE4C5),
                    MIDDLE_GRADIENT_OFFSET to Color(0xFF0015E6),
                ),
            )
    )
}

fun Modifier.rotatingGradientFill(rotation: Float, brush: Brush) =
    drawWithCache {
        onDrawWithContent {
            drawContent()
            rotate(rotation) {
                val maxDimension = max(size.width, size.height)
                drawRect(
                    brush,
                    topLeft = Offset(-maxDimension, -maxDimension),
                    size = Size(3 * maxDimension, 3 * maxDimension),
                    blendMode = BlendMode.SrcIn,
                )
            }
        }
    }
