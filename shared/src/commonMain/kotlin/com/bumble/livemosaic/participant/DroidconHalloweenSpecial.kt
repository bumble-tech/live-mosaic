package com.bumble.livemosaic.participant

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage
import kotlin.math.max

private const val TEXT_STARTING_GRADIENT_OFFSET = 0f
private const val TEXT_MIDDLE_GRADIENT_OFFSET = 70f
private const val TEXT_COLOR_PRIMARY = 0xFF642C91
private const val TEXT_COLOR_SECONDARY = 0xFFF36F21
private const val BACKGROUND_GRADIENT_OFFSET = 0.4f

@Composable
fun DroidconLondonHalloweenSpecial() {

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.White,
                    BACKGROUND_GRADIENT_OFFSET to Color.Black,
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Have a Spooky\nDroidcon London '23",
            modifier = Modifier
                .padding(top = 56.dp)
                .rotatingGradientFill(
                    rotation = rotation,
                    brush = Brush.linearGradient(
                        TEXT_STARTING_GRADIENT_OFFSET to Color(TEXT_COLOR_PRIMARY),
                        TEXT_MIDDLE_GRADIENT_OFFSET to Color(TEXT_COLOR_SECONDARY),
                    ),
                ),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        EmbeddableResourceImage(
            path = "participant/android_carved.png",
            contentDescription = "Carved Android Pumpkin",
            modifier = Modifier
                .size(80.dp),
        )
    }
}

fun Modifier.rotatingGradientFill(rotation: Float, brush: Brush) =
    graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                rotate(rotation) {
                    val maxDimension = max(size.width, size.height)
                    drawRect(
                        brush,
                        topLeft = Offset(-maxDimension, -maxDimension),
                        size = Size(4 * maxDimension, 4 * maxDimension),
                        blendMode = BlendMode.SrcAtop,
                    )
                }
            }
        }
