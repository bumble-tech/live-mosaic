package com.bumble.puzzyx.node.puzzle2

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.collections.immutableListOf
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.appyx.component.stream.Stream
import com.bumble.puzzyx.appyx.component.stream.operation.hide
import com.bumble.puzzyx.appyx.component.stream.operation.reveal
import com.bumble.puzzyx.composable.AutoPlayScript
import com.bumble.puzzyx.composable.EntryCard
import com.bumble.puzzyx.model.StreamLine
import com.bumble.puzzyx.model.entries
import com.bumble.puzzyx.ui.LocalAutoPlayFlow

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow / 15,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class Puzzle2Node(
    buildContext: BuildContext,
    private val stream: Stream = Stream(
        streamLine = StreamLine(-2.4f, entryIds = immutableListOf(0, 1, 2, 3, 4, 5, 6)),
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    )
) : ParentNode<Int>(
    buildContext = buildContext,
    appyxComponent = stream
) {

    override fun resolve(entryIdx: Int, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            EntryCard(
                modifier = modifier
                    .size(240.dp)
                    .aspectRatio(1.5f),
                entry = entries[entryIdx],
            )
        }

    @Composable
    override fun View(modifier: Modifier) {
        AutoPlayScript(
            steps = listOf(
                { stream.reveal() } to 2000,
            ),
            initialDelayMs = 2000,
            onFinish = { finish() }
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            AppyxComponent(
                appyxComponent = stream,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .aspectRatio(1f)
            )
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawLine(
                    Color.Red,
                    start = Offset(size.width / 2f, 0f),
                    end = Offset(size.width / 2f, size.height),
                    strokeWidth = density,
                )
                drawLine(
                    Color.Red,
                    start = Offset(0f, size.height / 2f),
                    end = Offset(size.width, size.height / 2f),
                    strokeWidth = density,
                )
            }
            Controls(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Controls(
        modifier: Modifier = Modifier,
    ) {
        if (!LocalAutoPlayFlow.current.collectAsState().value) {
            FlowRow(
                modifier = modifier,
                horizontalArrangement = spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Button(onClick = { stream.reveal(animationSpec = tween(2000)) }) {
                    Text("Reveal")
                }
                Button(onClick = { stream.hide(animationSpec = tween(2000)) }) {
                    Text("Hide")
                }
            }
        }
    }
}
