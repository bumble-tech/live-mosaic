package com.bumble.puzzyx.appyx.component.gridpuzzle.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.BaseOperation
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.appyx.component.gridpuzzle.GridPuzzle
import com.bumble.puzzyx.appyx.component.gridpuzzle.GridPuzzleModel
import com.bumble.puzzyx.appyx.component.gridpuzzle.GridPuzzleModel.State

@Parcelize
class Carousel(
    override var mode: Operation.Mode
) : BaseOperation<State>() {

    override fun isApplicable(state: GridPuzzleModel.State): Boolean =
        true

    override fun createFromState(baseLineState: GridPuzzleModel.State): GridPuzzleModel.State =
        baseLineState

    override fun createTargetState(fromState: GridPuzzleModel.State): GridPuzzleModel.State =
        fromState.copy(
            puzzleMode = GridPuzzleModel.PuzzleMode.CAROUSEL
        )
}

fun GridPuzzle.carousel(
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Carousel(mode), animationSpec = animationSpec)
}
