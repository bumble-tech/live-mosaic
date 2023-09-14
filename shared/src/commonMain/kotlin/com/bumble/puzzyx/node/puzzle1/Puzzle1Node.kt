package com.bumble.puzzyx.node.puzzle1

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.component.gridpuzzle.GridPuzzle
import com.bumble.puzzyx.component.gridpuzzle.operation.assemble
import com.bumble.puzzyx.component.gridpuzzle.operation.disassemble
import com.bumble.puzzyx.puzzle.PuzzlePiece
import com.bumble.puzzyx.ui.colors
import kotlinx.coroutines.delay
import kotlin.random.Random

private val gridCols = 16 // TODO get rid of this, move width into TargetUiState
private val gridRows = 9 // TODO get rid of this, move width into TargetUiState

class Puzzle1Node(
    buildContext: BuildContext,
    private val gridPuzzle: GridPuzzle = GridPuzzle(
        gridRows = gridRows,
        gridCols = gridCols,
        pieces = IntRange(0, gridRows * gridCols - 1).map {
            PuzzlePiece(it % gridCols, it / gridCols, "Puzzle piece 0, 0")
        },
//            listOf(
//            PuzzlePiece(0, 0, "Puzzle piece 0, 0"),
//            PuzzlePiece(0, 1, "Puzzle piece 0, 1"),
//        ),
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
                    .fillMaxWidth(1f / gridCols)
                    .fillMaxHeight(1f / gridRows)
                    .background(color)
            ) {
                Text("${interactionTarget.i},${interactionTarget.j}")
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        LaunchedEffect(Unit) {
            delay(2500)
            gridPuzzle.assemble(
                animationSpec = spring(
                    stiffness = Spring.StiffnessVeryLow / 30,
                    dampingRatio = Spring.DampingRatioNoBouncy
                )
            )
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppyxComponent(
                appyxComponent = gridPuzzle,
                modifier = Modifier
                    .aspectRatio(1f * gridCols / gridRows)
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )
            Controls()
        }
    }

    @Composable
    private fun Controls() {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { gridPuzzle.disassemble() }) {
                Text("Initial")
            }
            Button(onClick = { gridPuzzle.assemble() }) {
                Text("Assembled")
            }
            Button(onClick = { gridPuzzle.assemble() }) {
                Text("Flipped")
            }
        }
    }
}
