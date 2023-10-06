package com.bumble.puzzyx.node.linesofcards

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
import com.bumble.appyx.navigation.collections.immutableListOf
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.model.CardId

class StackedLinesOfCardsNode(
    buildContext: BuildContext,
) : ParentNode<StackedLinesOfCardsNode.InteractionTarget>(
    buildContext = buildContext,
    appyxComponent = PermanentAppyxComponent(model = PermanentModel(buildContext.savedStateMap))
) {
    sealed class InteractionTarget : Parcelable {
        @Parcelize
        data class LineOfCards(
            val messages: ImmutableList<CardId>,
            val delay: Long = 0L,
        ) : InteractionTarget()
    }

    override fun resolve(interactionTarget: InteractionTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            is InteractionTarget.LineOfCards -> LineOfCardsNode(
                buildContext = buildContext,
                messages = interactionTarget.messages,
                initialDelay = interactionTarget.delay
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            LineOfCards(
                initialDelay = 0,
                messages = immutableListOf(
                    CardId(0),
                    CardId(1),
                    CardId(2),
                    CardId(3),
                    CardId(4),
                    CardId(5),
                    CardId(6),
                )
            )
            LineOfCards(
                initialDelay = 5000,
                messages = immutableListOf(
                    CardId(7),
                    CardId(8),
                    CardId(9),
                    CardId(10),
                    CardId(11),
                    CardId(12),
                    CardId(13),
                )
            )
        }
    }

    @Composable
    private fun LineOfCards(
        initialDelay: Int = 0,
        messages: ImmutableList<CardId>,
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
            interactionTarget = InteractionTarget.LineOfCards(
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
