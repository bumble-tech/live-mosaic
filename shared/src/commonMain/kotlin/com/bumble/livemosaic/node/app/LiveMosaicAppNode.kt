@file:Suppress("MagicNumber")
package com.bumble.livemosaic.node.app

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.replace
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.integration.LocalScreenSize
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.livemosaic.appyx.component.backstackclipper.BackStackClipper
import com.bumble.livemosaic.composable.AutoPlayScript
import com.bumble.livemosaic.composable.CallToActionScreen
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC1
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC2
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC3
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.CallToAction
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.ManuelTalk
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.Mosaic1
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.Mosaic2
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.Mosaic3
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.StackedMessages
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.StarField
import com.bumble.livemosaic.node.app.LiveMosaicAppNode.NavTarget.ZsoltTalk
import com.bumble.livemosaic.node.messages.StackedMessagesNode
import com.bumble.livemosaic.node.mosaic.MosaicNode
import com.bumble.livemosaic.node.starfield.StarFieldNode
import com.bumble.livemosaic.ui.DottedMeshShape
import com.bumble.livemosaic.ui.LocalAutoPlayFlow
import kotlin.random.Random

private val screens = listOfNotNull(
    Mosaic1,
    CallToAction,
    getRandomVisualization(),
    ZsoltTalk,
    Mosaic2,
    CallToAction,
    getRandomVisualization(),
    ZsoltTalk,
    Mosaic3,
    CallToAction,
    getRandomVisualization(),
    ZsoltTalk,
)

private fun getRandomVisualization() =
    if (Random(123).nextBoolean()) StackedMessages else StarField

class LiveMosaicAppNode(
    buildContext: BuildContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTargets = listOf(screens.first()),
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackClipper(it, shape = { progress -> clipShape(progress) }) }
    )
) : ParentNode<NavTarget>(
    buildContext = buildContext,
    appyxComponent = backStack
) {
    private var screenIdx = 0

    sealed class NavTarget : Parcelable {
        @Parcelize
        data object Mosaic1 : NavTarget()

        @Parcelize
        data object Mosaic2 : NavTarget()

        @Parcelize
        data object Mosaic3 : NavTarget()

        @Parcelize
        data object StackedMessages : NavTarget()

        @Parcelize
        data object CallToAction : NavTarget()

        @Parcelize
        data object StarField : NavTarget()

        @Parcelize
        data object ZsoltTalk : NavTarget()

        @Parcelize
        data object ManuelTalk : NavTarget()
    }


    override fun resolve(navTarget: NavTarget, buildContext: BuildContext): Node =
        when (navTarget) {
            is Mosaic1 -> MosaicNode(
                config = MOSAIC1,
                buildContext = buildContext
            )

            is Mosaic2 -> MosaicNode(
                config = MOSAIC2,
                buildContext = buildContext
            )

            is Mosaic3 -> MosaicNode(
                config = MOSAIC3,
                buildContext = buildContext
            )

            is CallToAction -> node(buildContext) { modifier ->
                AutoPlayScript(initialDelayMs = 7500) { nextScreen() }
                CallToActionScreen(modifier)
            }

            is StarField -> StarFieldNode(buildContext)

            is StackedMessages -> StackedMessagesNode(buildContext)

            is ZsoltTalk -> node(buildContext) {
                AutoPlayScript(initialDelayMs = 10000) { nextScreen() }
                EmbeddableResourceImage(
                    path = "bumble/zsolt_talk.png",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is ManuelTalk -> node(buildContext) {
                AutoPlayScript(initialDelayMs = 10000) { nextScreen() }
                EmbeddableResourceImage(
                    path = "bumble/manuel_talk.png",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    override fun onChildFinished(child: Node) {
        nextScreen()
    }

    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            val isAutoPlayOn = remember { mutableStateOf(true) }

            CompositionLocalProvider(LocalAutoPlayFlow provides isAutoPlayOn) {
                CurrentScreen()
                Row {
                    AutoPlayToggle(
                        isAutoPlayOn = isAutoPlayOn.value,
                        toggleAutoPlay = {
                            isAutoPlayOn.value = !isAutoPlayOn.value
                        }
                    )
                    NextButton()
                }
            }
        }
    }

    @Composable
    private fun CurrentScreen() {
        AppyxComponent(
            appyxComponent = backStack,
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    private fun AutoPlayToggle(isAutoPlayOn: Boolean, toggleAutoPlay: () -> Unit) {
        Button(
            onClick = toggleAutoPlay,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(
                imageVector = if (isAutoPlayOn) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "Toggle auto-play",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.alpha(if (isAutoPlayOn) 0.035f else 1f),
            )
        }
    }

    @Composable
    private fun NextButton() {
        if (!LocalAutoPlayFlow.current.value) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { nextScreen() }
            ) {
                Icon(
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = "Next screen",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }

    private fun nextScreen() {
        backStack.replace(
            target = screens[++screenIdx % screens.size],
            animationSpec = tween(
                durationMillis = 3000,
                easing = FastOutLinearInEasing
            )
        )
    }
}

@Composable
private fun clipShape(progress: Float): Shape {
    val screenSize = LocalScreenSize.current
    val (meshMin, meshMax) = 14 to 25
    val meshSizeX = if (screenSize.widthDp > screenSize.heightDp) meshMax else meshMin
    val meshSizeY = if (screenSize.widthDp > screenSize.heightDp) meshMin else meshMax

    val shape by remember(progress) {
        mutableStateOf(
            DottedMeshShape(
                meshSizeX,
                meshSizeY,
                progress
            )
        )
    }

    return shape
}
