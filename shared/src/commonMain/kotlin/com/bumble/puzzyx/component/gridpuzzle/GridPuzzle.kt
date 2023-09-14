package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.Elements
import com.bumble.appyx.interactions.core.asElements
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.puzzle.PuzzlePiece
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.INITIAL

class GridPuzzleModel(
    savedStateMap: SavedStateMap? = null,
    pieces: List<PuzzlePiece>
) : BaseTransitionModel<PuzzlePiece, GridPuzzleModel.State>(
    savedStateMap = savedStateMap
) {

    enum class PuzzleMode {
        INITIAL, ASSEMBLED, FLIPPED
    }

    @Parcelize
    data class State(
        val puzzleMode: PuzzleMode = INITIAL,
        val elements: Elements<PuzzlePiece> = listOf()
    ) : Parcelable

    override val initialState: State =
        State(
            elements = pieces.asElements()
        )

    override fun State.availableElements(): Set<Element<PuzzlePiece>> =
        elements.toSet()

    override fun State.destroyedElements(): Set<Element<PuzzlePiece>> =
        emptySet()

    override fun State.removeDestroyedElement(element: Element<PuzzlePiece>): State =
        this

    override fun State.removeDestroyedElements(): State =
        this
}
