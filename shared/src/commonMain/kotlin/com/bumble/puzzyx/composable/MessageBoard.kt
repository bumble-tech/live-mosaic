package com.bumble.puzzyx.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.ui.colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import math.noise3D
import kotlin.math.cos
import kotlin.random.Random

@Composable
fun MessageBoard(modifier: Modifier) {
    ProgressBasedContent(
        millisPerFrame = 50000,
    ) { time ->
        MessageBoardContent(time, modifier)
    }
}

@Composable
private fun MessageBoardContent(time: Float, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(64.dp)
    ) {
        val maxX = 4
        val maxY = 3
        val xList = remember { (0..maxX).shuffled() }
        val yList = remember { (0..maxY).shuffled() }

        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val u = 1.0 * x / maxX
                val v = 1.0 * y / maxY
                val uShuffled = 1.0 * xList[x] / maxX
                val vShuffled = 1.0 * yList[y] / maxY
                val noise = noise3D(uShuffled, vShuffled, cos(Math.PI * 2 * time)).toFloat()

                Box(
                    modifier = Modifier
                        .size(290.dp)
                        .aspectRatio(1.5f)
                        .align(BiasAbsoluteAlignment((u * 2 - 1).toFloat(), (v * 2 - 1).toFloat()))
                        .padding(12.dp)
                ) {
                    val random = remember { Random.nextInt(4) }
                    AnimatedVisibility(
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                        visible = noise > 0.02f,
                    ) {
                        val entry = remember { Entry() }
                        val colorIdx = remember { colors.indices.random() }
                        EntryCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    colors[colorIdx],
                                    RoundedCornerShape(16.dp)
                                ),
                            entry
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressBasedContent(
    millisPerFrame: Int,
    initialDelayMillis: Long = 0,
    frameDelayMillis: Long = 0,
    content: @Composable (progress: Float) -> Unit
) {
    val time = remember { Animatable(0f) }
    var inProgress by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(initialDelayMillis)
        while (isActive) {
            delay(frameDelayMillis)
            inProgress = true
            time.animateTo(
                targetValue = time.value + 1f,
                animationSpec = tween(
                    durationMillis = millisPerFrame,
                    easing = LinearEasing
                ),
            ) {
                inProgress = false
            }
        }
    }

    val fraction = time.value - time.value.toInt()
    // To account for actually reaching 100% at the end rather than jumping back to 0%:
    val extra = if (!inProgress && time.value > 0 && time.value == time.value.toInt()
            .toFloat()
    ) 1 else 0
    val progress = fraction + extra

    content(progress)
}
