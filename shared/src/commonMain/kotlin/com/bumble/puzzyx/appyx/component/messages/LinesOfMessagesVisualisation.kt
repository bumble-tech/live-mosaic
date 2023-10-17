package com.bumble.puzzyx.appyx.component.messages

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.Alpha
import com.bumble.appyx.interactions.core.ui.property.impl.RotationX
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.ElementState.CREATED
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.ElementState.FLIPPED
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.ElementState.REVEALED
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.State
import com.bumble.puzzyx.model.MessageId

class LinesOfMessagesVisualisation(
    uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float>,
    private val parity: Int,
) : BaseMotionController<MessageId, State, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec
) {

    private val created = TargetUiState(
        rotationX = RotationX.Target(0f),
        scale = Scale.Target(1.2f),
        alpha = Alpha.Target(0f),
        position = PositionInside.Target(),
    )

    private val revealed = TargetUiState(
        rotationX = RotationX.Target(0f),
        scale = Scale.Target(1f),
        alpha = Alpha.Target(1f),
        position = PositionInside.Target(),
    )

    private val flipped = TargetUiState(
        rotationX = RotationX.Target(-90f),
        scale = Scale.Target(0.8f),
        alpha = Alpha.Target(0f),
        position = PositionInside.Target(),
    )

    override fun mutableUiStateFor(
        uiContext: UiContext,
        targetUiState: TargetUiState
    ): MutableUiState =
        targetUiState.toMutableState(uiContext)


    override fun State.toUiTargets(): List<MatchedTargetUiState<MessageId, TargetUiState>> {
        val xLength = 250f * transitionBounds.density.density * elements.size / 3456f
        val yLength = 200f * transitionBounds.density.density / 2160f
        val xBias = xLength / (elements.size - 1)
        return elements.entries.mapIndexed { index, entry ->
            val xNewBias = -xLength / 2f + xBias * index
            val yNewBias = if (index % 2 == parity) yLength else -yLength
            MatchedTargetUiState(
                element = entry.key,
                targetUiState = when (entry.value) {
                    CREATED -> created.withUpdatedPosition(xNewBias, yNewBias)
                    REVEALED -> revealed.withUpdatedPosition(xNewBias, yNewBias)
                    FLIPPED -> flipped.withUpdatedPosition(xNewBias, yNewBias)
                },
            )
        }
    }

    private fun TargetUiState.withUpdatedPosition(horizontalBias: Float, verticalBias: Float) =
        copy(
            position = PositionInside.Target(
                alignment = BiasAlignment.InsideAlignment(
                    horizontalBias = horizontalBias,
                    verticalBias = verticalBias,
                )
            )
        )
}
