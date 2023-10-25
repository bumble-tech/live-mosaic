package com.bumble.livemosaic.appyx.component.messages.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.livemosaic.appyx.component.messages.Messages
import com.bumble.livemosaic.appyx.component.messages.MessagesModel.ElementState

@Parcelize
class Reveal(override val entryId: Int, override var mode: Operation.Mode) : BaseMessagesModelOperation(entryId, mode) {
    override val targetElementState: ElementState = ElementState.REVEALED
}

fun Messages.reveal(
    entryId: Int,
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Reveal(entryId, mode), animationSpec = animationSpec)
}

