package com.bumble.puzzyx.appyx.component.messages

import androidx.compose.animation.core.SpringSpec
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
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
import kotlin.math.nextUp

class LinesOfMessagesVisualisation(
    uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float>,
    private val parity: Int,
    private val entrySize: DpSize,
    private val entryPadding: DpSize,
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
        val effectiveEntrySize = entrySize.plus(entryPadding)
        val maxEntryWidth = effectiveEntrySize.width * elements.size
        val halfEffectiveEntrySize = effectiveEntrySize / 2f
        return elements.entries.mapIndexed { index, entry ->
            /**
             * This code determines the positioning of entries on the screen. Entries are arranged in two rows,
             * alternating between rows as we iterate through the list. For each entry, we offset it by half
             * of its effective width along the X-axis. The effective width is calculated by adding the entry
             * width and padding.
             *
             * This arrangement results in the following pattern for entry indices:
             *
             *     Row 1:    1   3   5
             *     Row 2:  0   2   4   6
             *
             * The choice of the initial row alternates based on the parity, giving us an alternative pattern:
             *
             *     Row 1:  0   2   4   6
             *     Row 2:    1   3   5
             */
            val effectiveMaxWidth = (effectiveEntrySize.width * (elements.size / 2f).nextUp()) / 2f
            val horizontalOffset = -effectiveMaxWidth + halfEffectiveEntrySize.width * index
            val verticalOffset = if (index % 2 == parity) {
                halfEffectiveEntrySize.height
            } else {
                -halfEffectiveEntrySize.height
            }
            MatchedTargetUiState(
                element = entry.key,
                targetUiState = when (entry.value) {
                    CREATED -> created.withUpdatedPosition(horizontalOffset, verticalOffset)
                    REVEALED -> revealed.withUpdatedPosition(horizontalOffset, verticalOffset)
                    FLIPPED -> flipped.withUpdatedPosition(horizontalOffset, verticalOffset)
                },
            )
        }
    }

    private fun TargetUiState.withUpdatedPosition(horizontalBias: Dp, verticalBias: Dp) =
        copy(
            position = PositionInside.Target(
                alignment = BiasAlignment.InsideAlignment.Center,
                offset = DpOffset(horizontalBias, verticalBias)
            )
        )

}
