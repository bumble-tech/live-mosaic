package com.bumble.puzzyx.node.messages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.interactions.permanent.PermanentAppyxComponent
import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList
import com.bumble.appyx.navigation.composable.PermanentChild
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.MessageId
import com.bumble.puzzyx.model.entries

class StackedMessagesNode(
    buildContext: BuildContext,
    private val groupSize: Int = DEFAULT_GROUP_SIZE,
    private val stackOfMessages: List<ImmutableList<MessageId>> = buildList {
        val messageIds =
            entries.indices
                .map { MessageId(it) }

        // Take groups of groupSize messages.
        messageIds.windowed(groupSize, groupSize, false)
            .map { add(it.toImmutableList()) }

        // If there is still missing messages, take groupSize from the tail, potentially
        // repeating some of them.
        add(messageIds.takeLast(groupSize).toImmutableList())
    },
    private val permanentAppyxComponent: PermanentAppyxComponent<InteractionTarget> = PermanentAppyxComponent(
        savedStateMap = buildContext.savedStateMap,
        initialTargets = List(stackOfMessages.size) { index -> InteractionTarget.Messages(index) },
    ),
) : ParentNode<StackedMessagesNode.InteractionTarget>(
    buildContext = buildContext,
    appyxComponent = permanentAppyxComponent
) {

    sealed class InteractionTarget : Parcelable {
        @Parcelize
        data class Messages(val index: Int) : InteractionTarget()
    }

    override fun resolve(interactionTarget: InteractionTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            is InteractionTarget.Messages -> MessagesNode(
                buildContext = buildContext,
                index = interactionTarget.index,
                messages = stackOfMessages[interactionTarget.index],
                onFinished = { if (it == (stackOfMessages.size - 1) * 5000L) finish() }
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            stackOfMessages.forEachIndexed { index, _ ->
                PermanentChild(
                    permanentAppyxComponent = permanentAppyxComponent,
                    interactionTarget = InteractionTarget.Messages(
                        index = index,
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private companion object {
        const val DEFAULT_GROUP_SIZE = 7
    }
}
