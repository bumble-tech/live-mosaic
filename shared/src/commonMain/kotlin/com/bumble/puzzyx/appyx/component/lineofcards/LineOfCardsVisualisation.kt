package com.bumble.puzzyx.appyx.component.lineofcards

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.Alpha
import com.bumble.appyx.interactions.core.ui.property.impl.RotationX
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel.ElementState.VANISHED
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel.ElementState.INITIAL
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel.ElementState.REVEALED
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel.State
import com.bumble.puzzyx.model.CardId

class LineOfCardsVisualisation(
    uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float>
) : BaseMotionController<CardId, State, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec
) {

    override fun mutableUiStateFor(
        uiContext: UiContext,
        targetUiState: TargetUiState
    ): MutableUiState =
        targetUiState.toMutableState(uiContext)


    override fun State.toUiTargets(): List<MatchedTargetUiState<CardId, TargetUiState>> {
        val xLength = 1.05f
        val yBias = 0.115f
        val xBias = xLength / (elements.size - 1)
        return elements.entries.mapIndexed { index, entry ->
            val xNewBias = -xLength / 2f + xBias * index
            val yNewBias = if (index % 2 == 0) yBias else -yBias
            MatchedTargetUiState(
                element = entry.key,
                targetUiState = when (entry.value) {
                    INITIAL -> initial(xNewBias, yNewBias)
                    REVEALED -> revealed(xNewBias, yNewBias)
                    VANISHED -> vanished(xNewBias, yNewBias)
                },
            )
        }
    }

    private fun initial(
        xBias: Float,
        yBias: Float,
    ) = TargetUiState(
        rotationX = RotationX.Target(0f),
        position = PositionInside.Target(
            alignment = BiasAlignment.InsideAlignment(
                horizontalBias = xBias,
                verticalBias = yBias,
            )
        ),
        scale = Scale.Target(1.2f),
        alpha = Alpha.Target(0f),
    )

    private fun revealed(
        xBias: Float,
        yBias: Float,
    ) = TargetUiState(
        rotationX = RotationX.Target(0f),
        position = PositionInside.Target(
            alignment = BiasAlignment.InsideAlignment(
                horizontalBias = xBias,
                verticalBias = yBias,
            )
        ),
        scale = Scale.Target(1f),
        alpha = Alpha.Target(1f),
    )

    private fun vanished(
        xBias: Float,
        yBias: Float,
    ) = TargetUiState(
        rotationX = RotationX.Target(-90f),
        position = PositionInside.Target(
            alignment = BiasAlignment.InsideAlignment(
                horizontalBias = xBias,
                verticalBias = yBias,
            )
        ),
        scale = Scale.Target(0.8f),
        alpha = Alpha.Target(0f),
    )
}
