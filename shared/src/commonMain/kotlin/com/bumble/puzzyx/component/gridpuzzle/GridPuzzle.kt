package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.puzzyx.puzzle.PuzzlePiece

class GridPuzzle(
    savedStateMap: SavedStateMap? = null,
    gridRows: Int,
    gridCols: Int,
    pieces: List<PuzzlePiece>
) : BaseAppyxComponent<PuzzlePiece, GridPuzzleModel.State>(
    model = GridPuzzleModel(savedStateMap, gridRows, gridCols, pieces),
    motionController = { GridPuzzleVisualisation(it) },
) {

}
