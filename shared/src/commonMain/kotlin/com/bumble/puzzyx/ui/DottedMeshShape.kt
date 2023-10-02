package com.bumble.puzzyx.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.bumble.appyx.interactions.core.annotations.FloatRange
import com.bumble.appyx.interactions.core.ui.math.clamp
import com.bumble.appyx.interactions.core.ui.math.lerpFloat
import com.bumble.puzzyx.math.mapValueRange
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Creates a shape that contains [meshSizeX] * [meshSizeY] evenly spaced individual
 * circles.
 *
 * Expecting a [progress] value that represents the state of the animation in the [0f..1f] range,
 * each circle's radius will be calculated for the current frame such that:
 * - the starting value for radius is 0
 * - the maximum radius is [maxRadius]
 * - the radius animation for a given circle is delayed based on its position in the mesh,
 *   center ones starting first, gradually followed by ones closer to the edges
 *
 * The animation itself does not happen in this class, it only represents one frame given
 * the passed in [progress] value.
 */
class DottedMeshShape(
    private val meshSizeX: Int,
    private val meshSizeY: Int,
    private val maxRadius: Float,
    @FloatRange(from = 0.0, to = 1.0)
    private val progress: Float = 0f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val (width, height) = size
        val progressDelayed = lerpFloat(-1.0f, 1f, progress)

        val sheet = Path().apply {
            addRect(Rect(0f, 0f, width, height))
        }
        val appliedMeshSizeX = meshSizeX - 1f
        val appliedMeshSizeY = meshSizeY - 1f
        val halfMeshWidth = 0.5f * (width / appliedMeshSizeX)
        val halfMeshHeight = 0.5f * (height / appliedMeshSizeY)
        val clampRadius = sqrt(halfMeshWidth * halfMeshWidth + halfMeshHeight * halfMeshHeight)
        val dots = Path().apply {
            for (y in 0 until meshSizeY) {
                for (x in 0 until meshSizeX) {
                    val u = x / appliedMeshSizeX
                    val v = y / appliedMeshSizeY

                    val center = Offset(
                        x = lerpFloat(0f, width, u),
                        y = lerpFloat(0f, height, v)
                    )

                    val radius = clamp(
                        x = mapValueRange(
                            value = progressDelayed
                                    + (0.5f - abs(u - 0.5f))
                                    + (0.5f - abs(v - 0.5f)),
                            fromRangeMin = 0f,
                            fromRangeMax = 2f,
                            destRangeMin = 0f,
                            destRangeMax = maxRadius
                        ),
                        min = 0f,
                        max = clampRadius,
                    )

                    // Clip with ovals when you see them, but stick with rectangles otherwise. This
                    // helps make clipping faster and prevents dropping frames.
                    if (radius < clampRadius) {
                        addOval(
                            Rect(
                                left = center.x - radius,
                                top = center.y - radius,
                                right = center.x + radius,
                                bottom = center.y + radius,
                            ),
                        )
                    } else {
                        addRect(
                            Rect(
                                left = center.x - halfMeshWidth,
                                top = center.y - halfMeshHeight,
                                right = center.x + halfMeshWidth,
                                bottom = center.y + halfMeshHeight,
                            ),
                        )
                    }

                }
            }
        }

        val diff = Path().apply {
            op(sheet, dots, PathOperation.Difference)
        }

        return Outline.Generic(diff)
    }
}

