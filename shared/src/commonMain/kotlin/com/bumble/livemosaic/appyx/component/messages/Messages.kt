package com.bumble.livemosaic.appyx.component.messages

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.Visualisation
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.livemosaic.model.MessageId

class Messages(
    savedStateMap: SavedStateMap? = null,
    messages: List<MessageId>,
    visualisation: (UiContext) -> Visualisation<MessageId, MessagesModel.State>,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<MessageId, MessagesModel.State>(
    model = MessagesModel(savedStateMap, messages),
    visualisation = visualisation,
    defaultAnimationSpec = defaultAnimationSpec
)
