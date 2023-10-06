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
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.MessageId

class StackedMessagesNode(
    buildContext: BuildContext,
    val stackOfMessages: List<ImmutableList<MessageId>>,
) : ParentNode<StackedMessagesNode.InteractionTarget>(
    buildContext = buildContext,
    appyxComponent = PermanentAppyxComponent(model = PermanentModel(buildContext.savedStateMap))
) {
    sealed class InteractionTarget : Parcelable {
        @Parcelize
        data class Messages(
            val messages: ImmutableList<MessageId>,
            val delay: Long = 0L,
        ) : InteractionTarget()
    }

    override fun resolve(interactionTarget: InteractionTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            is InteractionTarget.Messages -> MessagesNode(
                buildContext = buildContext,
                messages = interactionTarget.messages,
                initialDelay = interactionTarget.delay
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            stackOfMessages.forEachIndexed { index, messages ->
                Messages(
                    initialDelay = 5000 * index,
                    messages = messages
                )
            }
        }
    }

    @Composable
    private fun Messages(
        initialDelay: Int = 0,
        messages: ImmutableList<MessageId>,
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
}
