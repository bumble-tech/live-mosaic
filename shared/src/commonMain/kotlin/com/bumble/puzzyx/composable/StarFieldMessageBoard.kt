package com.bumble.puzzyx.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bumble.appyx.interactions.core.ui.math.smoothstep
import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList
import com.bumble.puzzyx.composable.StarField.Companion.generateStars
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.model.entries
import com.bumble.puzzyx.ui.appyx_dark
import kotlinx.coroutines.isActive
import kotlin.random.Random

@Immutable
private data class StarFieldSpecs(
    val regularStarCounter: Int = 200,
    val speed: Float = 0.1f,
    val zNewCoord: Float = 0f,
    val zFadeInStart: Float = 0.3f,
    val zFadeInEnd: Float = 0.4f,
    val zFadeOutStart: Float = 1.3f,
    val zFadeOutEnd: Float = 1.4f,
)

@Immutable
private sealed class Star {
    abstract val xCoord: Float
    abstract val yCoord: Float
    abstract val zCoord: Float

    data class EntryStar(
        override val xCoord: Float = Random.nextDouble(-1.0, 1.0).toFloat(),
        override val yCoord: Float = Random.nextDouble(-1.0, 1.0).toFloat(),
        override val zCoord: Float,
        val entry: Entry,
    ) : Star()

    data class RegularStar(
        override val xCoord: Float = Random.nextDouble(-1.0, 1.0).toFloat(),
        override val yCoord: Float = Random.nextDouble(-1.0, 1.0).toFloat(),
        override val zCoord: Float,
        val color: Color,
    ) : Star()
}

@Immutable
private data class StarField(
    val specs: StarFieldSpecs,
    val stars: ImmutableList<Star>
) {
    companion object {
        fun generateStars(starFieldSpecs: StarFieldSpecs): StarField =
            StarField(
                specs = starFieldSpecs,
                stars = (entryStars(starFieldSpecs) + regularStars(starFieldSpecs))
                    .shuffled()
                    .toImmutableList()
            )

        private fun entryStars(starFieldSpecs: StarFieldSpecs) =
            entries.map {
                Star.EntryStar(
                    zCoord = Random.nextDouble(
                        from = starFieldSpecs.zNewCoord.toDouble(),
                        until = starFieldSpecs.zFadeOutEnd.toDouble(),
                    ).toFloat(),
                    entry = it,
                )
            }

        private fun regularStars(starFieldSpecs: StarFieldSpecs) =
            Array(starFieldSpecs.regularStarCounter) {
                Star.RegularStar(
                    zCoord = Random.nextDouble(
                        from = starFieldSpecs.zNewCoord.toDouble(),
                        until = starFieldSpecs.zFadeOutEnd.toDouble(),
                    ).toFloat(),
                    color = Color(
                        red = Random.nextDouble(0.60, 0.66).toFloat(),
                        green = Random.nextDouble(0.60, 0.66).toFloat(),
                        blue = Random.nextDouble(0.97, 1.0).toFloat(),
                    ),
                )
            }
    }
}


private fun StarField.update(
    timeInSecs: Float
): StarField =
    copy(
        stars = stars.map { star ->
            val zNewCoord =
                (star.zCoord + specs.speed * timeInSecs).takeIf { it < specs.zFadeOutEnd }
                    ?: specs.zNewCoord
            when (star) {
                is Star.EntryStar -> star.copy(zCoord = zNewCoord)
                is Star.RegularStar -> star.copy(zCoord = zNewCoord)
            }
        }.toImmutableList()
    )

@Composable
fun StarFieldMessageBoard(
    modifier: Modifier = Modifier,
) {
    val starFieldSpecs = remember { StarFieldSpecs() }
    var starField by remember { mutableStateOf(generateStars(starFieldSpecs)) }
    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (isActive) {
            withFrameMillis {
                if (lastFrame == 0L) {
                    lastFrame = it
                }
                starField = starField.update(
                    timeInSecs = (it - lastFrame) / 1_000f,
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
    Box(modifier = modifier) {
        starField.stars.forEach { star ->
            val zPos = star.zCoord
            val xPos = star.xCoord * zPos
            val yPos = star.yCoord * zPos
            val alpha = smoothstep(starField.specs.zFadeInStart, starField.specs.zFadeInEnd, zPos) -
                    smoothstep(starField.specs.zFadeOutStart, starField.specs.zFadeOutEnd, zPos)

            StarContent(
                star,
                modifier = Modifier
                    .scale(zPos)
                    .size(290.dp)
                    .aspectRatio(1.5f)
                    .align(BiasAlignment(xPos, yPos))
                    .alpha(alpha)
                    .zIndex(zPos)
            )
        }
    }
}

@Composable
private fun StarContent(
    star: Star,
    modifier: Modifier = Modifier,
) {
    when (star) {
        is Star.EntryStar -> EntryStarContent(star, modifier)
        is Star.RegularStar -> RegularStarContent(star, modifier)
    }
}

@Composable
private fun EntryStarContent(
    star: Star.EntryStar,
    modifier: Modifier = Modifier,
) {
    EntryCard(
        entry = star.entry,
        modifier = modifier
    )
}

@Composable
private fun RegularStarContent(
    star: Star.RegularStar,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
    ) {
        drawCircle(color = star.color, radius = density * 2f)
    }
}
