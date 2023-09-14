package com.bumble.puzzyx.node.puzzle1

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.puzzyx.puzzle.PuzzlePiece

class Puzzle1Node(
    buildContext: BuildContext,
    private val backStack: BackStack<PuzzlePiece> = BackStack(
        model = BackStackModel(
            initialTargets = listOf(PuzzlePiece(1, 1, "Puzzle piece 1, 1")),
            savedStateMap = buildContext.savedStateMap,
        ),
        motionController = { BackStackSlider(it) }
    )
) : ParentNode<PuzzlePiece>(
    buildContext = buildContext,
    appyxComponent = backStack
) {

    override fun resolve(interactionTarget: PuzzlePiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            Text(interactionTarget.text)
        }

    @Composable
    override fun View(modifier: Modifier) {
        AppyxComponent(
            appyxComponent = backStack,
            modifier = modifier
                .fillMaxSize()
        )
    }
}
