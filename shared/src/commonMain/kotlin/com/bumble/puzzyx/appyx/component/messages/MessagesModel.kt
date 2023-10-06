package com.bumble.puzzyx.appyx.component.messages

import com.bumble.appyx.interactions.core.Element
import com.bumble.appyx.interactions.core.asElement
import com.bumble.appyx.interactions.core.model.transition.BaseTransitionModel
import com.bumble.appyx.navigation.state.SavedStateMap
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.MessageId
import com.bumble.puzzyx.model.MessageList

class MessagesModel(
    savedStateMap: SavedStateMap? = null,
    messageList: MessageList,
) : BaseTransitionModel<MessageId, MessagesModel.State>(
    savedStateMap = savedStateMap
) {

    @Parcelize
    data class State(
        val elements: Map<Element<MessageId>, ElementState>,
    ) : Parcelable

    enum class ElementState {
        INITIAL, REVEALED, VANISHED
    }

    override val initialState: State by lazy {
        State(
            elements = messageList.messages.associate { it.asElement() to ElementState.INITIAL },
        )
    }

    override fun State.availableElements(): Set<Element<MessageId>> =
        elements.keys.toSet()

    override fun State.destroyedElements(): Set<Element<MessageId>> =
        emptySet()

    override fun State.removeDestroyedElements(): State =
        this

    override fun State.removeDestroyedElement(element: Element<MessageId>): State =
        this
}
