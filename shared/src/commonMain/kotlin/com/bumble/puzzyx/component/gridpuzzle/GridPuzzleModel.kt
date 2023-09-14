package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.Elements
import com.bumble.appyx.interactions.core.asElements
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.INITIAL
import com.bumble.puzzyx.puzzle.PuzzlePiece

class GridPuzzleModel : BaseTransitionModel<PuzzlePiece, GridPuzzleModel.State> {

    constructor(savedStateMap: SavedStateMap? = null, pieces: List<PuzzlePiece>) : super(
        savedStateMap = savedStateMap
    ) {
        this.initialState = State(
            pieces = pieces.asElements()
        )
    }

    enum class PuzzleMode {
        INITIAL, ASSEMBLED, FLIPPED
    }

    @Parcelize
    data class State(
        val puzzleMode: PuzzleMode = INITIAL,
        val pieces: Elements<PuzzlePiece> = listOf()
    ) : Parcelable

    override val initialState: State

    override fun State.availableElements(): Set<Element<PuzzlePiece>> =
        pieces.toSet()

    override fun State.destroyedElements(): Set<Element<PuzzlePiece>> =
        emptySet()

    override fun State.removeDestroyedElement(element: Element<PuzzlePiece>): State =
        this

    override fun State.removeDestroyedElements(): State =
        this
}
