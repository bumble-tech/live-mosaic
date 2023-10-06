package com.bumble.puzzyx.appyx.component.lineofcards

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.asElement
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.CardId
import com.bumble.puzzyx.model.Line

class LineOfCardsModel(
    savedStateMap: SavedStateMap? = null,
    line: Line,
) : BaseTransitionModel<CardId, LineOfCardsModel.State>(
    savedStateMap = savedStateMap
) {

    @Parcelize
    data class State(
        val elements: Map<Element<CardId>, ElementState>,
    ) : Parcelable

    enum class ElementState {
        INITIAL, REVEALED, VANISHED
    }

    override val initialState: State by lazy {
        State(
            elements = line.cards.associate { it.asElement() to ElementState.INITIAL },
        )
    }

    override fun State.availableElements(): Set<Element<CardId>> =
        elements.keys.toSet()

    override fun State.destroyedElements(): Set<Element<CardId>> =
        emptySet()

    override fun State.removeDestroyedElements(): State =
        this

    override fun State.removeDestroyedElement(element: Element<CardId>): State =
        this
}
