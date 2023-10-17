package com.bumble.puzzyx.node.messages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.appyx.interactions.permanent.PermanentAppyxComponent
import com.bumble.appyx.interactions.permanent.PermanentModel
import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList
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
) : ParentNode<StackedMessagesNode.InteractionTarget>(
    buildContext = buildContext,
    appyxComponent = PermanentAppyxComponent(model = PermanentModel(buildContext.savedStateMap))
) {
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
    }

    sealed class InteractionTarget : Parcelable {
        @Parcelize
        data class Messages(
            val index: Int,
            val messages: List<MessageId>,
            val delay: Long = 0L,
        ) : InteractionTarget()
    }

    override fun resolve(interactionTarget: InteractionTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            is InteractionTarget.Messages -> MessagesNode(
                buildContext = buildContext,
                index = interactionTarget.index,
                messages = interactionTarget.messages,
                initialDelay = interactionTarget.delay,
                onFinished = { if (it == (stackOfMessages.size - 1) * 5000L) finish() }
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            stackOfMessages.forEachIndexed { index, messages ->
                Messages(
                    index = index,
                    messages = messages,
                    initialDelay = 5000 * index,
                )
            }
        }
    }

    @Composable
    private fun Messages(
        index: Int,
        messages: ImmutableList<MessageId>,
        initialDelay: Int = 0,
    ) {
        val yOffset = remember { Animatable(-400f) }
        LaunchedEffect(Unit) {
            yOffset.animateTo(
                targetValue = 200f,
                animationSpec = tween(
                    delayMillis = initialDelay,
                    durationMillis = 10000,
                    easing = LinearEasing
                ),
            )
        }
        PermanentChild(
            interactionTarget = InteractionTarget.Messages(
                index = index,
                messages = messages,
                delay = initialDelay.toLong(),
            )
        ) { child ->
            child(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = yOffset.value.dp)
            )
        }
    }

    private companion object {
        const val DEFAULT_GROUP_SIZE = 7
    }
}
