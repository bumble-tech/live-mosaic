package com.bumble.puzzyx.appyx.component.messages.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.appyx.component.messages.Messages
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.ElementState

@Parcelize
class Flip(override val entryId: Int, override var mode: Operation.Mode) : BaseMessagesModelOperation(entryId, mode) {
    override val targetElementState: ElementState = ElementState.FLIPPED
}

fun Messages.flip(
    entryId: Int,
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Flip(entryId, mode), animationSpec = animationSpec)
}

