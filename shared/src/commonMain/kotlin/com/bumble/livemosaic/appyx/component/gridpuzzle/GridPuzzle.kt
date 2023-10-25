package com.bumble.livemosaic.appyx.component.gridpuzzle

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.livemosaic.model.PuzzlePiece

class GridPuzzle(
    savedStateMap: SavedStateMap? = null,
    gridRows: Int,
    gridCols: Int,
    pieces: List<PuzzlePiece>,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<PuzzlePiece, GridPuzzleModel.State>(
    model = GridPuzzleModel(savedStateMap, gridRows, gridCols, pieces),
    visualisation = { GridPuzzleVisualisation(it, defaultAnimationSpec) },
    defaultAnimationSpec = defaultAnimationSpec
)
