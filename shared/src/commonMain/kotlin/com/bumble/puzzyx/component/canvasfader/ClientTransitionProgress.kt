package com.bumble.puzzyx.component.canvasfader

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Easing
import androidx.compose.ui.Modifier
import com.bumble.appyx.interactions.core.ui.math.lerpFloat
import com.bumble.appyx.interactions.core.ui.property.Interpolatable
import com.bumble.appyx.interactions.core.ui.property.MotionProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * With Appyx, we usually animate actual UI-related properties like Alpha, Rotation, etc.
 *
 * This class represents an animatable Float that holds no immediate ui effect.
 * Instead, client code can read its value and decide what it wants to do
 * with it.
 */
class ClientTransitionProgress(
    coroutineScope: CoroutineScope,
    target: Target,
    displacement: StateFlow<Float> = MutableStateFlow(0f),
) : MotionProperty<Float, AnimationVector1D>(
    coroutineScope = coroutineScope,
    animatable = Animatable(target.value),
    displacement = displacement
), Interpolatable<ClientTransitionProgress.Target> {

    class Target(
        val value: Float,
        val easing: Easing? = null,
    ) :  MotionProperty.Target

    override fun calculateRenderValue(base: Float, displacement: Float): Float =
        base - displacement

    override val modifier: Modifier
        get() = Modifier

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
