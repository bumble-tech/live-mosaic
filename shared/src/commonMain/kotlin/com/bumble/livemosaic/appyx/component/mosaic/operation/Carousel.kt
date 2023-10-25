package com.bumble.livemosaic.appyx.component.mosaic.operation

import androidx.compose.animation.core.AnimationSpec
import com.bumble.appyx.interactions.core.model.transition.BaseOperation
import com.bumble.appyx.interactions.core.model.transition.Operation
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.livemosaic.appyx.component.mosaic.MosaicComponent
import com.bumble.livemosaic.appyx.component.mosaic.MosaicModel
import com.bumble.livemosaic.appyx.component.mosaic.MosaicModel.State

@Parcelize
class Carousel(
    override var mode: Operation.Mode
) : BaseOperation<State>() {

    override fun isApplicable(state: State): Boolean =
        true

    override fun createFromState(baseLineState: State): State =
        baseLineState

    override fun createTargetState(fromState: State): State =
        fromState.copy(
            mosaicMode = MosaicModel.MosaicMode.CAROUSEL
        )
}

fun MosaicComponent.carousel(
    mode: Operation.Mode = Operation.Mode.IMMEDIATE,
    animationSpec: AnimationSpec<Float>? = null
) {
    operation(operation = Carousel(mode), animationSpec = animationSpec)
}
