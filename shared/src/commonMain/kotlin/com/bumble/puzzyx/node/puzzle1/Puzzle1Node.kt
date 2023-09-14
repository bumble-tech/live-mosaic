package com.bumble.puzzyx.node.puzzle1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzle
import com.bumble.puzzyx.puzzle.PuzzlePiece
import com.bumble.puzzyx.ui.colors
import kotlin.random.Random
import kotlin.random.Random.Default

private val gridRows = 4 // TODO get rid of this, move width into TargetUiState
private val gridCols = 4 // TODO get rid of this, move width into TargetUiState

class Puzzle1Node(
    buildContext: BuildContext,
    private val gridPuzzle: GridPuzzle = GridPuzzle(
        gridRows = gridRows,
        gridCols = gridCols,
        pieces = listOf(
            PuzzlePiece(0, 0, "Puzzle piece 0, 0"),
            PuzzlePiece(0, 1, "Puzzle piece 0, 1"),
            PuzzlePiece(0, 2, "Puzzle piece 0, 2"),
            PuzzlePiece(0, 3, "Puzzle piece 0, 3"),
            PuzzlePiece(1, 0, "Puzzle piece 1, 0"),
            PuzzlePiece(1, 1, "Puzzle piece 1, 1"),
            PuzzlePiece(1, 2, "Puzzle piece 1, 2"),
            PuzzlePiece(1, 3, "Puzzle piece 1, 3"),
            PuzzlePiece(2, 0, "Puzzle piece 2, 0"),
            PuzzlePiece(2, 1, "Puzzle piece 2, 1"),
            PuzzlePiece(2, 2, "Puzzle piece 2, 2"),
            PuzzlePiece(2, 3, "Puzzle piece 2, 3"),
            PuzzlePiece(3, 0, "Puzzle piece 3, 0"),
            PuzzlePiece(3, 1, "Puzzle piece 3, 1"),
            PuzzlePiece(3, 2, "Puzzle piece 3, 2"),
            PuzzlePiece(3, 3, "Puzzle piece 3, 3"),
        ),
        savedStateMap = buildContext.savedStateMap,
    )
) : ParentNode<PuzzlePiece>(
    buildContext = buildContext,
    appyxComponent = gridPuzzle
) {

    override fun resolve(interactionTarget: PuzzlePiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            val colorIdx = rememberSaveable(interactionTarget) { Random.nextInt(colors.size) }
            val color = colors[colorIdx]

            Box(
                modifier = modifier
                    .fillMaxWidth(1f / gridRows)
                    .fillMaxHeight(1f / gridCols)
                    .background(color)
            ) {
                Text("${interactionTarget.i},${interactionTarget.j}")
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {
            AppyxComponent(
                appyxComponent = gridPuzzle,
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .background(Color.LightGray)
            )
        }
    }
}
