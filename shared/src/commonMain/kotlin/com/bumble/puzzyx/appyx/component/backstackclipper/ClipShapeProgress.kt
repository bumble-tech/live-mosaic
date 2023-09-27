package com.bumble.puzzyx.appyx.component.backstackclipper

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.bumble.appyx.interactions.core.ui.math.lerpFloat
import com.bumble.appyx.interactions.core.ui.property.Interpolatable
import com.bumble.appyx.interactions.core.ui.property.MotionProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * With Appyx, we usually animate actual UI-related properties like Alpha, Rotation, etc.
 *
 * This class wraps an animatable Float that should represent an animation progress value
 * in the 0..1f range. Using this animated value, a [Shape] is fetched as a function of progress
 * and applied as clip mask with Modifier.clip(shape).
 */
class ClipShapeProgress(
    coroutineScope: CoroutineScope,
    target: Target,
    displacement: StateFlow<Float> = MutableStateFlow(0f),
    private val shape: @Composable (progress: Float) -> Shape = { RectangleShape },
) : MotionProperty<Float, AnimationVector1D>(
    coroutineScope = coroutineScope,
    animatable = Animatable(target.value),
    displacement = displacement
), Interpolatable<ClipShapeProgress.Target> {

    class Target(
        val value: Float,
        val easing: Easing? = null,
    ) :  MotionProperty.Target

    override fun calculateRenderValue(base: Float, displacement: Float): Float =
        base - displacement

    override val modifier: Modifier
        get() = Modifier.composed {
            val progress = renderValueFlow.collectAsState().value
            if (progress == 0f) this
            else this.clip(shape.invoke(progress))
        }


    override suspend fun lerpTo(start: Target, end: Target, fraction: Float) {
        snapTo(
            lerpFloat(
                start = start.value,
                end = end.value,
                progress = easingTransform(end.easing, fraction)
            )
        )
    }
}
