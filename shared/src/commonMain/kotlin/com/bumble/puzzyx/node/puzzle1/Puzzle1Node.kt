package com.bumble.puzzyx.node.puzzle1

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.interactions.core.model.transition.Operation.Mode.KEYFRAME
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzle
import com.bumble.puzzyx.component.gridpuzzle.operation.assemble
import com.bumble.puzzyx.component.gridpuzzle.operation.carousel
import com.bumble.puzzyx.component.gridpuzzle.operation.flip
import com.bumble.puzzyx.component.gridpuzzle.operation.scatter
import com.bumble.puzzyx.composable.EntryCard
import com.bumble.puzzyx.composable.FlashCard
import com.bumble.puzzyx.entries.Entry
import com.bumble.puzzyx.puzzle.PuzzlePiece
import com.bumble.puzzyx.ui.colors
import kotlin.random.Random

private val gridCols = 16 // TODO get rid of this, move width into TargetUiState
private val gridRows = 9 // TODO get rid of this, move width into TargetUiState

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow / 15,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class Puzzle1Node(
    buildContext: BuildContext,
    private val gridPuzzle: GridPuzzle = GridPuzzle(
        gridRows = gridRows,
        gridCols = gridCols,
        pieces = IntRange(0, gridRows * gridCols - 1).map {
            PuzzlePiece(it % gridCols, it / gridCols, Entry())
        }.shuffled(),//.take(37), // TODO To test only a subset of elements, uncomment .take
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    )
) : ParentNode<PuzzlePiece>(
    buildContext = buildContext,
    appyxComponent = gridPuzzle
) {

    override fun resolve(puzzlePiece: PuzzlePiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            val colorIdx = rememberSaveable(puzzlePiece) { Random.nextInt(colors.size) }
            val color = colors[colorIdx]

            Box(
                modifier = modifier
                    .fillMaxWidth(1f / gridCols)
                    .fillMaxHeight(1f / gridRows)
            ) {
                FlashCard(
                    flash = Color.White,
                    front = { modifier ->
                        Box(modifier = modifier
                            .fillMaxSize()
                            .background(color)
                        ) {
                            Text("${puzzlePiece.i},${puzzlePiece.j}")
                        }
                    },
                    back = { modifier ->
                        EntryCard(
                            modifier = modifier
                                .fillMaxSize()
                                .background(color) ,
                            puzzlePiece.entry
                        )
                    }
                )
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        LaunchedEffect(Unit) {
            // We can add the scripted state changes here
            // delay(2500)
            // gridPuzzle.assemble()
            // etc.
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            AppyxComponent(
                appyxComponent = gridPuzzle,
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f * gridCols / gridRows)
                    .background(Color.DarkGray)
            )
            Controls(
                modifier = Modifier.align(BottomCenter)
            )
        }
    }

    @Composable
    private fun Controls(modifier: Modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { gridPuzzle.scatter() }) {
                Text("Scatter")
            }
            Button(onClick = { gridPuzzle.assemble() }) {
                Text("Assemble")
            }
            Button(onClick = { gridPuzzle.flip(KEYFRAME, tween(10000)) }) {
                Text("Flip")
            }
            Button(onClick = { gridPuzzle.carousel() }) {
                Text("Carousel")
            }
        }
    }
}
