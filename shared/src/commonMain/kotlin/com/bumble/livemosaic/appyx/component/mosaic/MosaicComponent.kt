package com.bumble.livemosaic.appyx.component.mosaic

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.livemosaic.model.MosaicPiece

class MosaicComponent(
    savedStateMap: SavedStateMap? = null,
    gridRows: Int,
    gridCols: Int,
    pieces: List<MosaicPiece>,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<MosaicPiece, MosaicModel.State>(
    model = MosaicModel(savedStateMap, gridRows, gridCols, pieces),
    visualisation = { MosaicVisualisation(it, defaultAnimationSpec) },
    defaultAnimationSpec = defaultAnimationSpec
)
