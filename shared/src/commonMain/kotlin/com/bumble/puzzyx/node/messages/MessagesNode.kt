package com.bumble.puzzyx.node.messages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.integration.LocalScreenSize
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.appyx.component.messages.LinesOfMessagesVisualisation
import com.bumble.puzzyx.appyx.component.messages.Messages
import com.bumble.puzzyx.appyx.component.messages.operation.flip
import com.bumble.puzzyx.appyx.component.messages.operation.reveal
import com.bumble.puzzyx.composable.AutoPlayScript
import com.bumble.puzzyx.composable.EntryCard
import com.bumble.puzzyx.model.MessageId
import com.bumble.puzzyx.model.MessageList
import com.bumble.puzzyx.model.entries
import kotlin.random.Random

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class MessagesNode(
    buildContext: BuildContext,
    private val nodeId: Int,
    private val messages: List<MessageId>,
    private val component: Messages = Messages(
        messageList = MessageList(
            messages = messages,
        ),
        motionController = {
            LinesOfMessagesVisualisation(
                uiContext = it,
                defaultAnimationSpec = animationSpec,
                parity = nodeId % 2
            )
        },
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    ),
    private val onFinished: (Long) -> Unit,
    private val initialDelay: Long = 0L,
) : ParentNode<MessageId>(
    buildContext = buildContext,
    appyxComponent = component
) {

    override fun resolve(interactionTarget: MessageId, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            EntryCard(
                modifier = modifier
                    .scale(LocalScreenSize.current.widthDp.value / 1728f)
                    .size(240.dp, 160.dp),
                entry = entries[interactionTarget.entryId],
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        key(initialDelay) {
            AutoPlayScript(
                steps = buildList {
                    val reorderedMessages = messages.shuffled()
                    reorderedMessages.forEachIndexed { index, messageId ->
                        val duration = if (index != messages.size - 1) 200L else 2000L
                        add({ component.reveal(messageId.entryId) } to duration)
                    }
                    reorderedMessages.forEachIndexed { index, messageId ->
                        val duration = if (index != messages.size - 1) 200L else 2000L
                        add({ component.flip(messageId.entryId) } to duration)
                    }
                },
                initialDelayMs = 4000 + initialDelay,
                onFinish = { onFinished(initialDelay) }
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val sign = remember { if (Random.nextBoolean()) 1f else -1f }
                val targetRotationXY = remember { -sign * (2f + 4f * Random.nextFloat()) }
                val rotationZ = remember { sign * (1.5f + 1.5f * Random.nextFloat()) }
                val rotationXY = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    rotationXY.animateTo(
                        targetValue = targetRotationXY,
                        animationSpec = tween(
                            durationMillis = 6000,
                            delayMillis = (4000 + initialDelay).toInt(),
                            easing = FastOutSlowInEasing,
                        ),
                    )
                }
                AppyxComponent(
                    appyxComponent = component,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .graphicsLayer(
                            rotationX = rotationXY.value / 2f,
                            rotationY = rotationXY.value,
                            rotationZ = rotationZ,
                        ),
                )
            }
        }
    }
}
