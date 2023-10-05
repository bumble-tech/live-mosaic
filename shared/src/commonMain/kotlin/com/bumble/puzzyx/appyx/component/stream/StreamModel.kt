package com.bumble.puzzyx.appyx.component.stream

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.asElement
import com.bumble.appyx.interactions.core.asElements
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.StreamLine

class StreamModel(
    savedStateMap: SavedStateMap? = null,
    streamLine: StreamLine,
) : BaseTransitionModel<Int, StreamModel.State>(
    savedStateMap = savedStateMap
) {

    enum class StreamLineMode {
        INITIAL, REVEALED,
    }

    @Parcelize
    data class State(
        val mode: StreamLineMode,
        val streamLine: Element<StreamLine>,
    ) : Parcelable

    override val initialState: State by lazy {
        State(
            mode = StreamLineMode.INITIAL,
            streamLine = streamLine.asElement(),
        )
    }

    override fun State.availableElements(): Set<Element<Int>> =
        streamLine.interactionTarget.entryIds.asElements().toSet()

    override fun State.destroyedElements(): Set<Element<Int>> =
        emptySet()

    override fun State.removeDestroyedElements(): State =
        this

    override fun State.removeDestroyedElement(element: Element<Int>): State =
        this
}
