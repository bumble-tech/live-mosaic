package com.bumble.livemosaic.node.mosaic

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
import com.bumble.livemosaic.appyx.component.mosaic.MosaicComponent
import com.bumble.livemosaic.appyx.component.mosaic.operation.assemble
import com.bumble.livemosaic.appyx.component.mosaic.operation.carousel
import com.bumble.livemosaic.appyx.component.mosaic.operation.flip
import com.bumble.livemosaic.appyx.component.mosaic.operation.scatter
import com.bumble.livemosaic.composable.AutoPlayScript
import com.bumble.livemosaic.composable.FlashCard
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage
import com.bumble.livemosaic.model.MosaicConfig
import com.bumble.livemosaic.model.MosaicPiece
import com.bumble.livemosaic.model.mosaic1Entries
import com.bumble.livemosaic.model.mosaic2Entries
import com.bumble.livemosaic.model.mosaic3Entries
import com.bumble.livemosaic.ui.LocalAutoPlayFlow
import com.bumble.livemosaic.ui.appyx_dark
import com.bumble.livemosaic.ui.colors
import kotlin.random.Random

private val animationSpec = spring<Float>(
    stiffness = Spring.StiffnessVeryLow / 15,
    dampingRatio = Spring.DampingRatioNoBouncy
)

class MosaicNode(
    buildContext: BuildContext,
    private val config: MosaicConfig,
    private val mosaic: MosaicComponent = MosaicComponent(
        gridRows = config.rows,
        gridCols = config.columns,
        pieces = IntRange(0, config.rows * config.columns - 1)
            .shuffled(Random(123))
            .take(
                when (config) {
                    MosaicConfig.MOSAIC1 -> mosaic1Entries
                    MosaicConfig.MOSAIC2 -> mosaic2Entries
                    MosaicConfig.MOSAIC3 -> mosaic3Entries
                }.size
            )
            .mapIndexed { sequentialIdx, shuffledIdx ->
                MosaicPiece(
                    i = shuffledIdx % config.columns,
                    j = shuffledIdx / config.columns,
                    entryId = sequentialIdx
                )
            },
        savedStateMap = buildContext.savedStateMap,
        defaultAnimationSpec = animationSpec
    )
) : ParentNode<MosaicPiece>(
    buildContext = buildContext,
    appyxComponent = mosaic
) {

    override fun resolve(mosaicPiece: MosaicPiece, buildContext: BuildContext): Node =
        node(buildContext) { modifier ->
            val colorIdx = rememberSaveable(mosaicPiece) { Random.nextInt(colors.size) }

            Box(
                modifier = modifier
                    .fillMaxWidth(1f / config.columns)
                    .fillMaxHeight(1f / config.rows)
            ) {
                FlashCard(
                    flash = Color.White,
                    front = { modifier ->
                        EmbeddableResourceImage(
                            path = "${config.frontImagesDir}/slice_${mosaicPiece.j}_${mosaicPiece.i}.png",
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier
                                .fillMaxSize()
                        )
                    },
                    back = { modifier ->
                        EmbeddableResourceImage(
                            path = "${config.backImagesDir}/slice_${mosaicPiece.j}_${mosaicPiece.i}.png",
                            contentScale = ContentScale.FillBounds,
                            modifier = modifier
                                .fillMaxSize()
                        )
                    }
                )
            }
        }

    @Composable
    override fun View(modifier: Modifier) {
        AutoPlayScript(
            steps = listOf(
                { mosaic.assemble() } to 9000,
                { mosaic.flip(KEYFRAME, tween(10000)) } to 8000,
                { mosaic.scatter() } to 500,
            ),
            initialDelayMs = 2000,
            onFinish = {
                finish()
            }
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(appyx_dark)
                .padding(24.dp),
        ) {
            AppyxComponent(
                appyxComponent = mosaic,
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f * config.columns / config.rows)
                    .background(Color.DarkGray)
            )
            Controls(
                modifier = Modifier.align(BottomCenter)
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun Controls(
        modifier: Modifier = Modifier,
    ) {
        if (!LocalAutoPlayFlow.current.value) {
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { mosaic.scatter() }) {
                    Text("Scatter")
                }
                Button(onClick = { mosaic.assemble() }) {
                    Text("Assemble")
                }
                Button(onClick = { mosaic.flip(KEYFRAME, tween(10000)) }) {
                    Text("Flip")
                }
                Button(onClick = { mosaic.carousel() }) {
                    Text("Carousel")
                }
            }
        }
    }
}
