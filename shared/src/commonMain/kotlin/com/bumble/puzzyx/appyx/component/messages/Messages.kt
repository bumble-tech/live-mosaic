package com.bumble.puzzyx.appyx.component.messages

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.interactions.core.model.BaseAppyxComponent
import com.bumble.appyx.interactions.core.ui.MotionController
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.puzzyx.model.MessageId
import com.bumble.puzzyx.model.MessageList

class Messages(
    savedStateMap: SavedStateMap? = null,
    messageList: MessageList,
    motionController: (UiContext) -> MotionController<MessageId, MessagesModel.State>,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseAppyxComponent<MessageId, MessagesModel.State>(
    model = MessagesModel(savedStateMap, messageList),
    motionController = motionController,
    defaultAnimationSpec = defaultAnimationSpec
)
