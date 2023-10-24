package com.bumble.puzzyx.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumble.appyx.interactions.core.ui.math.smoothstep
import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList
import com.bumble.puzzyx.composable.StarField.Companion.generateStars
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.ui.appyx_dark
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt
import kotlin.random.Random

@Immutable
private data class StarFieldSpecs(
    val regularStarCounter: Int = 200,
    val maxEntries: Int = 12,
    val speed: Float = 0.1f,
    val zNewCoord: Float = -1f,
    val zFadeInStart: Float = 0.3f,
    val zFadeInEnd: Float = 0.4f,
    val zFadeOutStart: Float = 1.3f,
    val zFadeOutEnd: Float = 1.4f,
    val scaleFactor: Float,
) {
    val zOffset = (zFadeOutEnd - zFadeInStart) / maxEntries
}

private data class Star(
    val xCoord: Float = Random.nextDouble(-0.5, 0.5).toFloat(),
    val yCoord: Float = Random.nextDouble(-0.5, 0.5).toFloat(),
    val zCoord: Float,
    val sizeDp: Dp,
    val aspectRatio: Float = 1f,
    val type: StarType,
)

@Immutable
private sealed class StarType {
    data class RegularType(val color: Color) : StarType() {
        companion object {
            val sizeDp: Dp = 4.dp
        }
    }

    data class EntryType(val entry: Entry) : StarType() {
        companion object {
            val sizeDp: Dp = 200.dp
            const val aspectRatio: Float = 1.5f
        }
    }
}

@Immutable
private data class StarField(
    val specs: StarFieldSpecs,
    val stars: ImmutableList<Star>,
) {
    companion object {
        fun generateStars(
            starFieldSpecs: StarFieldSpecs,
            entries: ImmutableList<Entry>
        ): StarField =
            StarField(
                specs = starFieldSpecs,
                stars = (regularStars(starFieldSpecs)
                        + entryStars(starFieldSpecs, entries)
                        ).toImmutableList()
            )

        private fun regularStars(starFieldSpecs: StarFieldSpecs) =
            Array(starFieldSpecs.regularStarCounter) {
                Star(
                    zCoord = Random.nextDouble(
                        from = starFieldSpecs.zNewCoord.toDouble(),
                        until = starFieldSpecs.zFadeOutEnd.toDouble(),
                    ).toFloat(),
                    sizeDp = StarType.RegularType.sizeDp,
                    type = StarType.RegularType(
                        color = Color(
                            red = Random.nextDouble(0.60, 0.66).toFloat(),
                            green = Random.nextDouble(0.60, 0.66).toFloat(),
                            blue = Random.nextDouble(0.97, 1.0).toFloat(),
                        )
                    ),
                )
            }.toList()

        private fun entryStars(starFieldSpecs: StarFieldSpecs, entries: ImmutableList<Entry>) =
            entries.mapIndexed { index, entry ->
                Star(
                    zCoord = starFieldSpecs.zFadeInStart - index * starFieldSpecs.zOffset,
                    sizeDp = StarType.EntryType.sizeDp * starFieldSpecs.scaleFactor,
                    aspectRatio = StarType.EntryType.aspectRatio,
                    type = StarType.EntryType(entry = entry),
                )
            }
    }
}


private fun StarField.update(
    timeInSecs: Float,
    specs: StarFieldSpecs,
): StarField =
    copy(
        specs = specs,
        stars = stars.map { star ->
            val zUpdatedCoord = star.zCoord + specs.speed * timeInSecs
            if (zUpdatedCoord < specs.zFadeOutEnd) {
                star.copy(zCoord = zUpdatedCoord)
            } else {
                star.copy(
                    xCoord = Random.nextDouble(-0.5, 0.5).toFloat(),
                    yCoord = Random.nextDouble(-0.5, 0.5).toFloat(),
                    zCoord = specs.zFadeInStart,
                )
            }
        }.toImmutableList(),
    )

@Composable
fun StarFieldMessageBoard(
    entries: ImmutableList<Entry>,
    modifier: Modifier = Modifier,
) {
    val scaleFactor = scaleFactor()
    val starFieldSpecs = remember(scaleFactor, entries) {
        StarFieldSpecs(
            maxEntries = entries.size,
            scaleFactor = scaleFactor
        )
    }
    var starField by remember { mutableStateOf(generateStars(starFieldSpecs, entries)) }
    var running by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (isActive && running) {
            withFrameMillis {
                if (lastFrame == 0L) {
                    lastFrame = it
                }
                starField = starField.update(
                    timeInSecs = (it - lastFrame) / 1_000f,
                    specs = starFieldSpecs,
                )
                lastFrame = it
            }
        }
    }

    StarFieldContent(
        starField = starField,
        modifier = modifier
            .fillMaxSize()
            .background(appyx_dark)
    )
}

@Composable
private fun StarFieldContent(
    starField: StarField,
    modifier: Modifier = Modifier,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = modifier.onSizeChanged { size = it },
    ) {
        starField.stars.forEachIndexed { index, star ->
            key(index) {
                val zPos = star.zCoord
                val xPos = star.xCoord * zPos
                val yPos = star.yCoord * zPos
                val alpha = starField.specs.calcAlpha(zPos)
                if (alpha > 0f) {
                    StarContent(
                        star.type,
                        star.sizeDp,
                        modifier = Modifier
                            .scale(zPos)
                            .size(star.sizeDp)
                            .aspectRatio(star.aspectRatio)
                            .align(Alignment.Center)
                            .absoluteOffset {
                                IntOffset(
                                    x = (size.width * xPos).roundToInt(),
                                    y = (size.height * yPos).roundToInt(),
                                )
                            }
                            .alpha(alpha)
                            .zIndex(zPos)
                    )
                }
            }
        }
    }
}

private fun StarFieldSpecs.calcAlpha(zPos: Float) =
    smoothstep(zFadeInStart, zFadeInEnd, zPos) - smoothstep(zFadeOutStart, zFadeOutEnd, zPos)

@Composable
private fun StarContent(
    type: StarType,
    sizeDp: Dp,
    modifier: Modifier = Modifier,
) {
    when (type) {
        is StarType.RegularType -> RegularStarContent(type.color, modifier)
        is StarType.EntryType -> EntryStarContent(type.entry, sizeDp, modifier)
    }
}

@Composable
private fun RegularStarContent(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
    ) {
        drawCircle(color = color, radius = density * 2f)
    }
}

@Composable
private fun EntryStarContent(
    entry: Entry,
    sizeDp: Dp,
    modifier: Modifier = Modifier,
) {
    OptimisingLayout(
        optimalWidth = sizeDp,
        modifier = modifier,
    ) {
        EntryCard(
            entry = entry,
        )
    }
}
