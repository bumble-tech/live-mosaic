package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.InsideAlignment.Companion.fractionAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.State
import com.bumble.puzzyx.puzzle.PuzzlePiece

class GridPuzzleVisualisation(
    uiContext: UiContext
) : BaseMotionController<PuzzlePiece, State, MutableUiState, TargetUiState>(
    uiContext = uiContext
) {
    override fun mutableUiStateFor(
        uiContext: UiContext,
        targetUiState: TargetUiState
    ): MutableUiState =
        targetUiState.toMutableState(uiContext)

    override fun State.toUiTargets(): List<MatchedTargetUiState<PuzzlePiece, TargetUiState>> =
        pieces.map {
            val (i, j) = it.interactionTarget
            MatchedTargetUiState(
                element = it,
                targetUiState = TargetUiState(
                    position = PositionInside.Target(fractionAlignment(
                        horizontalBiasFraction = i * (1f / (gridRows - 1)),
                        verticalBiasFraction = j * (1f / (gridCols - 1))
                    )),
//                    width = Width.Target(1f / gridRows),
//                    height = Height.Target(1f / gridRows),
                )
            )
        }
}
