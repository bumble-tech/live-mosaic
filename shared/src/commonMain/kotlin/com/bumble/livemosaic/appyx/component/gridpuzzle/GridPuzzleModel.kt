package com.bumble.livemosaic.appyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.Elements
import com.bumble.appyx.interactions.core.asElements
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.livemosaic.appyx.component.gridpuzzle.GridPuzzleModel.PuzzleMode.SCATTERED
import com.bumble.livemosaic.model.PuzzlePiece

class GridPuzzleModel(
    savedStateMap: SavedStateMap? = null,
    gridRows: Int,
    gridCols: Int,
    pieces: List<PuzzlePiece>
) : BaseTransitionModel<PuzzlePiece, GridPuzzleModel.State>(
    savedStateMap = savedStateMap
) {

    enum class PuzzleMode {
        SCATTERED, ASSEMBLED, FLIPPED, CAROUSEL
    }

    @Parcelize
    data class State(
        val gridRows: Int,
        val gridCols: Int,
        val puzzleMode: PuzzleMode = SCATTERED,
        val pieces: Elements<PuzzlePiece> = listOf()
    ) : Parcelable {

        val maxNbElements: Int = gridRows * gridCols
    }

    override val initialState: State by lazy {
        require(pieces.all {
            it.i in 0 until gridCols && it.j in 0 until gridRows
        })

        State(
            gridRows = gridRows,
            gridCols = gridCols,
            pieces = pieces.asElements()
        )
    }

    override fun State.availableElements(): Set<Element<PuzzlePiece>> =
        pieces.toSet()

    override fun State.destroyedElements(): Set<Element<PuzzlePiece>> =
        emptySet()

    override fun State.removeDestroyedElement(element: Element<PuzzlePiece>): State =
        this

    override fun State.removeDestroyedElements(): State =
        this
}
