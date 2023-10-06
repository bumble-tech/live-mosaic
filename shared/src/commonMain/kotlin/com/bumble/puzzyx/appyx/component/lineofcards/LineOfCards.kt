package com.bumble.puzzyx.appyx.component.lineofcards

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.MotionController
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.puzzyx.model.CardId
import com.bumble.puzzyx.model.Line

class LineOfCards(
    savedStateMap: SavedStateMap? = null,
    line: Line,
    motionController: (UiContext) -> MotionController<CardId, LineOfCardsModel.State>,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<CardId, LineOfCardsModel.State>(
    model = LineOfCardsModel(savedStateMap, line),
    motionController = motionController,
    defaultAnimationSpec = defaultAnimationSpec
)
