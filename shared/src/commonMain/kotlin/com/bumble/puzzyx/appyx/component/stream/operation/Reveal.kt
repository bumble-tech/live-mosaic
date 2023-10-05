package com.bumble.puzzyx.appyx.component.stream.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.BaseOperation
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.appyx.component.stream.Stream
import com.bumble.puzzyx.appyx.component.stream.StreamModel
import com.bumble.puzzyx.appyx.component.stream.StreamModel.State

@Parcelize
class Reveal(
    override var mode: Operation.Mode
) : BaseOperation<State>() {
    override fun isApplicable(state: State): Boolean =
        true

    override fun createFromState(baseLineState: State): State =
        baseLineState

    override fun createTargetState(fromState: State): State =
        fromState.copy(mode = StreamModel.StreamLineMode.REVEALED)
}

fun Stream.reveal(
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Reveal(mode), animationSpec = animationSpec)
}

