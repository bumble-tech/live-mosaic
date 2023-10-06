package com.bumble.puzzyx.appyx.component.lineofcards.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.BaseOperation
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCards
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsModel.State

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
                    it.key to LineOfCardsModel.ElementState.INITIAL
                } else {
                    it.key to it.value
                }
            }
        )
}

fun LineOfCards.hide(
    entryId: Int,
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Hide(entryId, mode), animationSpec = animationSpec)
}

