package com.bumble.puzzyx.appyx.component.stream

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.puzzyx.appyx.component.gridpuzzle.GridPuzzleVisualisation
import com.bumble.puzzyx.model.StreamLine

class Stream(
    savedStateMap: SavedStateMap? = null,
    streamLine: StreamLine,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<Int, StreamModel.State>(
    model = StreamModel(savedStateMap, streamLine),
    motionController = { StreamMotionController(it, defaultAnimationSpec) },
    defaultAnimationSpec = defaultAnimationSpec
)
