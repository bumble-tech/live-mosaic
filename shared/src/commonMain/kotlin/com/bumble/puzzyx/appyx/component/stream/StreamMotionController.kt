package com.bumble.puzzyx.appyx.component.stream

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.asElement
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.Alpha
import com.bumble.appyx.interactions.core.ui.property.impl.RotationZ
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.appyx.component.stream.StreamModel.State
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class StreamMotionController(
    uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float>
) : BaseMotionController<Int, State, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec
) {

    override fun mutableUiStateFor(
        uiContext: UiContext,
        targetUiState: TargetUiState
    ): MutableUiState =
        targetUiState.toMutableState(uiContext)


    override fun State.toUiTargets(): List<MatchedTargetUiState<Int, TargetUiState>> =
        with(streamLine.interactionTarget) {
            val xLength = 1.05f
            val yBias = 0.115f
            val xBias = xLength / (entryIds.size - 1)
            val rotationInRads = toRadians(rotation.toDouble())
            val cs = cos(rotationInRads)
            val sn = sin(rotationInRads)
            entryIds.mapIndexed { index, entryId ->
                val xNewBias = -xLength / 2f + xBias * index
                val yNewBias = if (index % 2 == 0) yBias else -yBias
                val xBiasRotated = (cs * xNewBias - sn * yNewBias).toFloat()
                val yBiasRotated = (sn * xNewBias + cs * yNewBias).toFloat()
                MatchedTargetUiState(
                    element = entryId.asElement(),
                    targetUiState = when (mode) {
                        StreamModel.StreamLineMode.INITIAL -> initial(xBiasRotated, yBiasRotated)
                        StreamModel.StreamLineMode.REVEALED -> revealed(xBiasRotated, yBiasRotated)
                    },
                )
            }
        }

    private fun State.initial(
        xBias: Float,
        yBias: Float,
    ) = TargetUiState(
        rotationZ = RotationZ.Target(streamLine.interactionTarget.rotation),
        position = PositionInside.Target(
            alignment = BiasAlignment.InsideAlignment(
                horizontalBias = xBias,
                verticalBias = yBias,
            )
        ),
        scale = Scale.Target(0f),
        alpha = Alpha.Target(0f),
    )

    private fun State.revealed(
        xBias: Float,
        yBias: Float,
    ) = TargetUiState(
        rotationZ = RotationZ.Target(streamLine.interactionTarget.rotation),
        position = PositionInside.Target(
            alignment = BiasAlignment.InsideAlignment(
                horizontalBias = xBias,
                verticalBias = yBias,
            )
        ),
        scale = Scale.Target(1f),
        alpha = Alpha.Target(1f),
    )
}
