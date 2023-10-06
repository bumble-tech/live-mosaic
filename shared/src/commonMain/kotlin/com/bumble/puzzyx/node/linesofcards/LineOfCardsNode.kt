package com.bumble.puzzyx.node.linesofcards

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCards
import com.bumble.puzzyx.appyx.component.lineofcards.LineOfCardsVisualisation
import com.bumble.puzzyx.appyx.component.lineofcards.operation.flip
import com.bumble.puzzyx.appyx.component.lineofcards.operation.reveal
import com.bumble.puzzyx.composable.AutoPlayScript
import com.bumble.puzzyx.composable.EntryCard
import com.bumble.puzzyx.model.CardId
import com.bumble.puzzyx.model.Line
import com.bumble.puzzyx.model.entries
import kotlin.random.Random

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class LineOfCardsNode(
    buildContext: BuildContext,
    private val messages: ImmutableList<CardId>,
    private val lineOfCards: LineOfCards = LineOfCards(
        line = Line(
            cards = messages,
        ),
        motionController = {
            LineOfCardsVisualisation(
                uiContext = it,
                defaultAnimationSpec = animationSpec
            )
        },
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    ),
    private val initialDelay: Long = 0L,
) : ParentNode<CardId>(
    buildContext = buildContext,
    appyxComponent = lineOfCards
) {

    override fun resolve(interactionTarget: CardId, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            EntryCard(
                modifier = modifier
                    .size(240.dp)
                    .aspectRatio(1.5f),
                entry = entries[interactionTarget.entryId],
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        AutoPlayScript(
            steps = buildList {
                val reorderedMessages = messages.shuffled()
                reorderedMessages.forEachIndexed { index, messageId ->
                    val duration = if (index != messages.size - 1) 200L else 2000L
                    add({ lineOfCards.reveal(messageId.entryId) } to duration)
                }
                reorderedMessages.forEachIndexed { index, messageId ->
                    val duration = if (index != messages.size - 1) 200L else 2000L
                    add({ lineOfCards.flip(messageId.entryId) } to duration)
                }
            },
            initialDelayMs = 4000 + initialDelay,
            onFinish = { if (initialDelay != 0L) finish() }
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
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
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(600.dp, 300.dp)
                    .graphicsLayer(
                        rotationX = rotationXY.value / 2f,
                        rotationY = rotationXY.value,
                        rotationZ = rotationZ,
                    )
                    .background(Color.Red.copy(alpha = 0.2f)),
            )
            AppyxComponent(
                appyxComponent = lineOfCards,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .aspectRatio(1f)
                    .graphicsLayer(
                        rotationX = rotationXY.value / 2f,
                        rotationY = rotationXY.value,
                        rotationZ = rotationZ,
                    ),
            )
        }
    }
}
