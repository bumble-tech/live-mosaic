package com.bumble.puzzyx.component.gridpuzzle

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.times
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.math.smoothstep
import com.bumble.appyx.interactions.core.ui.property.impl.RotationY
import com.bumble.appyx.interactions.core.ui.property.impl.RotationZ
import com.bumble.appyx.interactions.core.ui.property.impl.position.BiasAlignment.InsideAlignment.Companion.fractionAlignment
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside.Target
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.ASSEMBLED
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.FLIPPED
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.INITIAL
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.State
import com.bumble.puzzyx.puzzle.PuzzlePiece
import kotlin.random.Random

class GridPuzzleVisualisation(
    private val uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float>
) : BaseMotionController<PuzzlePiece, State, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec
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
                targetUiState = when (puzzleMode) {
                    INITIAL -> initial(i, j)
                    ASSEMBLED -> assembled(i, j)
                    FLIPPED -> flipped(i, j)
                }
            )
        }

    private fun State.initial(i: Int, j: Int) = TargetUiState(
        position = Target(
            alignment = alignment(i, j),
            offset = DpOffset(
                x = (i - (gridCols - 1) / 2f) * Random.nextInt(3,9) * 0.5f * transitionBounds.widthDp,
                y = (j - (gridRows - 1) / 2f) * Random.nextInt(3,9) * 0.5f * transitionBounds.heightDp,
            ),
        ),
        rotationZ = RotationZ.Target(
            (if (Random.nextBoolean()) -1 else 1) * Random.nextInt(1, 4) * 360f
        )
    )

    private fun State.assembled(i: Int, j: Int) = TargetUiState(
        position = Target(
            alignment = alignment(i, j)
        ),
    )

    private fun State.flipped(i: Int, j: Int) = TargetUiState(
        position = Target(
            alignment = alignment(i, j),
        ),
        rotationY = RotationY.Target(180f, easing = gridEasing(i, j))
    )

    private fun State.gridEasing(i: Int, j: Int): Easing = Easing { fraction ->
        val overlap = 5
        val unit = 1f / (gridCols + overlap + gridRows)
        val length = overlap * unit
        val idx = (i + j * 0.5f)

        smoothstep(idx * unit, idx * unit + length, fraction)
    }

    private fun State.sequentialEasing(i: Int, j: Int): Easing = Easing { fraction ->
        val overlap = 5
        val unit = 1f / (gridRows * gridCols + overlap)
        val length = overlap * unit
        val idx = (j * gridRows + i).toFloat()

        smoothstep(idx * unit, idx * unit + length, fraction)
    }

    private fun State.alignment(
        i: Int,
        j: Int
    ) = fractionAlignment(
        horizontalBiasFraction = i * (1f / (gridCols - 1)),
        verticalBiasFraction = j * (1f / (gridRows - 1))
    )
}
