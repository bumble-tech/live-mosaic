package com.bumble.puzzyx.node.puzzle1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class Puzzle1Node(
    buildContext: BuildContext,
    private val gridPuzzle: GridPuzzle = GridPuzzle(
        pieces = listOf(PuzzlePiece(1, 1, "Puzzle piece 1, 1")),
        savedStateMap = buildContext.savedStateMap,
    )
) : ParentNode<PuzzlePiece>(
    buildContext = buildContext,
    appyxComponent = gridPuzzle
) {

    override fun resolve(interactionTarget: PuzzlePiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            Box(
                modifier = modifier
                    .then(Modifier.background(Color.Red))
            ) {
                Text(interactionTarget.text)
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                AppyxComponent(
                    appyxComponent = gridPuzzle,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
