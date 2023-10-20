package com.bumble.puzzyx.node.messages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
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
import com.bumble.puzzyx.composable.OptimisingLayout
import com.bumble.puzzyx.model.MessageId
import com.bumble.puzzyx.model.entries
import kotlinx.coroutines.async
import kotlin.random.Random

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class MessagesNode(
    buildContext: BuildContext,
    private val index: Int,
    private val messages: List<MessageId>,
    private val component: Messages = Messages(
        messages = messages,
        visualisation = {
            LinesOfMessagesVisualisation(
                uiContext = it,
                defaultAnimationSpec = animationSpec,
                parity = index % 2,
                entrySize = DpSize(
                    ENTRY_WIDTH.dp,
                    (ENTRY_WIDTH / ENTRY_ASPECT_RATIO).dp
                ),
                entryPadding = DpSize(ENTRY_PADDING.dp, ENTRY_PADDING.dp)
            )
        },
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    ),
    private val onFinished: (Int) -> Unit,
) : ParentNode<MessageId>(
    buildContext = buildContext,
    appyxComponent = component
) {

    override fun resolve(interactionTarget: MessageId, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                EntryCard(
                    entry = entries[interactionTarget.entryId],
                    modifier = Modifier
                        .size(ENTRY_WIDTH.dp, ENTRY_WIDTH.dp / ENTRY_ASPECT_RATIO)
                )
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        key(index) {
            val initialDelay = 8500L * index
            AutoPlayScript(
                steps = buildList {
                    val reorderedMessages = messages.shuffled()
                    revealMessages(reorderedMessages)
                    flipMessages(reorderedMessages)
                },
                initialDelayMs = 500 + initialDelay,
                onFinish = { onFinished(index) }
            )

            Box(
                modifier = modifier
            ) {
                val verticalBias = remember { Animatable(-0.35f) }
                val sign = remember { if (Random.nextBoolean()) 1f else -1f }
                val targetRotationXY = remember { -sign * (2f + 1f * Random.nextFloat()) }
                val rotationZ = remember { sign * (1.5f + 0.5f * Random.nextFloat()) }
                val rotationXY = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    async {
                        verticalBias.animateTo(
                            targetValue = 0.2f,
                            animationSpec = tween(
                                delayMillis = 8500 * index,
                                durationMillis = 13500,
                                easing = LinearEasing
                            ),
                        )
                    }
                    async {
                        rotationXY.animateTo(
                            targetValue = targetRotationXY,
                            animationSpec = tween(
                                delayMillis = (500 + initialDelay).toInt(),
                                durationMillis = 6000,
                                easing = FastOutSlowInEasing,
                            ),
                        )
                    }
                }
                OptimisingLayout(
                    optimalWidth = 1500.dp,
                    paddingFraction = 0f,
                ) {
                    val translationY = verticalBias.value * with(LocalDensity.current) {
                        LocalScreenSize.current.heightDp.toPx()
                    }
                    AppyxComponent(
                        appyxComponent = component,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                this.compositingStrategy = CompositingStrategy.Offscreen
                                this.rotationX = rotationXY.value / 2f
                                this.rotationY = rotationXY.value
                                this.rotationZ = rotationZ
                                this.translationY = translationY
                            }
                    )
                }
            }
        }
    }

    private fun MutableList<Pair<() -> Unit, Long>>.revealMessages(messages: List<MessageId>) {
        addOperation(messages) { reveal(it) }
    }

    private fun MutableList<Pair<() -> Unit, Long>>.flipMessages(messages: List<MessageId>) {
        addOperation(messages) { flip(it) }
    }

    private fun MutableList<Pair<() -> Unit, Long>>.addOperation(
        messages: List<MessageId>,
        operation: Messages.(Int) -> Unit,
    ) {
        messages.forEachIndexed { index, messageId ->
            val duration = if (index != messages.size - 1) 200L else 5500L
            add({ component.operation(messageId.entryId) } to duration)
        }
    }

    companion object {
        const val ENTRY_WIDTH = 240f
        const val ENTRY_ASPECT_RATIO = 1.5f
        const val ENTRY_PADDING = 8f
    }
}
