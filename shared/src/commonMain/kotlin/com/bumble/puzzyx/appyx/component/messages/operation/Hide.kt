package com.bumble.puzzyx.appyx.component.messages.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.BaseOperation
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.appyx.component.messages.Messages
import com.bumble.puzzyx.appyx.component.messages.MessagesModel
import com.bumble.puzzyx.appyx.component.messages.MessagesModel.State

@Parcelize
class Hide(
    private val entryId: Int,
    override var mode: Operation.Mode
) : BaseOperation<State>() {
    override fun isApplicable(state: State): Boolean =
        true

    override fun createFromState(baseLineState: State): State =
        baseLineState

    override fun createTargetState(fromState: State): State =
        fromState.copy(
            elements = fromState.elements.entries.associate {
                if (it.key.interactionTarget.entryId == entryId) {
                    it.key to MessagesModel.ElementState.INITIAL
                } else {
                    it.key to it.value
                }
            }
        )
}

fun Messages.hide(
    entryId: Int,
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Hide(entryId, mode), animationSpec = animationSpec)
}

