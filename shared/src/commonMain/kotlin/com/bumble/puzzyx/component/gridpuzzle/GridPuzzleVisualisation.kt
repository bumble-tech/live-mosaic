package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.Height
import com.bumble.appyx.interactions.core.ui.property.impl.Width
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.InsideAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.InsideAlignment.Companion
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

    override fun GridPuzzleModel.State.toUiTargets(): List<MatchedTargetUiState<PuzzlePiece, TargetUiState>> =
        pieces.map {
            MatchedTargetUiState(
                element = it,
                targetUiState = TargetUiState(
                    position = PositionInside.Target(InsideAlignment.Center),
                    width = Width.Target(0.1f),
                    height = Height.Target(0.1f),
                )
            )
        }
}
