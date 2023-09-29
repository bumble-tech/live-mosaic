package com.bumble.puzzyx.node.puzzle1

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumble.appyx.interactions.core.model.transition.Operation.Mode.KEYFRAME
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.appyx.component.gridpuzzle.GridPuzzle
import com.bumble.puzzyx.appyx.component.gridpuzzle.operation.assemble
import com.bumble.puzzyx.appyx.component.gridpuzzle.operation.carousel
import com.bumble.puzzyx.appyx.component.gridpuzzle.operation.flip
import com.bumble.puzzyx.appyx.component.gridpuzzle.operation.scatter
import com.bumble.puzzyx.composable.AutoPlayScript
import com.bumble.puzzyx.composable.EntryCardSmall
import com.bumble.puzzyx.composable.FlashCard
import com.bumble.puzzyx.imageloader.ResourceImage
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.model.Puzzle
import com.bumble.puzzyx.model.PuzzlePiece
import com.bumble.puzzyx.model.puzzle1Entries
import com.bumble.puzzyx.ui.LocalAutoPlayFlow
import com.bumble.puzzyx.ui.appyx_dark
import com.bumble.puzzyx.ui.colors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.random.Random

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow / 15,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class Puzzle1Node(
    buildContext: BuildContext,
    private val puzzle: Puzzle,
    private val gridPuzzle: GridPuzzle = GridPuzzle(
        gridRows = puzzle.rows,
        gridCols = puzzle.columns,
        pieces = IntRange(0, puzzle.rows * puzzle.columns - 1)
            .shuffled(Random(123))
            .take(puzzle1Entries.size)
            .mapIndexed { sequentialIdx, shuffledIdx ->
                PuzzlePiece(
                    i = shuffledIdx % puzzle.columns,
                    j = shuffledIdx / puzzle.columns,
                    entryId = sequentialIdx
                )
            },
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    )
) : ParentNode<PuzzlePiece>(
    buildContext = buildContext,
    appyxComponent = gridPuzzle
) {

    @OptIn(ExperimentalResourceApi::class)
    override fun resolve(puzzlePiece: PuzzlePiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            val colorIdx = rememberSaveable(puzzlePiece) { Random.nextInt(colors.size) }
            val color = colors[colorIdx]

            Box(
                modifier = modifier
                    .fillMaxWidth(1f / puzzle.columns)
                    .fillMaxHeight(1f / puzzle.rows)
            ) {
                FlashCard(
                    flash = Color.White,
                    front = { modifier ->
                        ResourceImage(
                            path = "${puzzle.imagesDir}/slice_${puzzlePiece.j}_${puzzlePiece.i}.png",
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier
                                .fillMaxSize()
                        )
                    },
                    back = { modifier ->
                        EntryCardSmall(
                            modifier = modifier
                                .fillMaxSize()
                                .background(color),
                            // TODO decide on the fate of this
                            entry = puzzle1Entries[puzzlePiece.entryId] as? Entry.Text ?: Entry.Text(Puzzle.PUZZLE1, "n/a")
                        )
                    }
                )
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        AutoPlayScript(
            steps = listOf(
                { gridPuzzle.assemble() } to 9000,
                { gridPuzzle.flip(KEYFRAME, tween(10000)) } to 8000,
                { gridPuzzle.scatter() } to 500,
            )
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(appyx_dark)
                .padding(24.dp),
        ) {
            AppyxComponent(
                appyxComponent = gridPuzzle,
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f * puzzle.columns / puzzle.rows)
                    .background(Color.DarkGray)
            )
            Controls(
                modifier = Modifier.align(BottomCenter)
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Controls(modifier: Modifier) {
        if (!LocalAutoPlayFlow.current.collectAsState().value) {
            FlowRow(
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
}
