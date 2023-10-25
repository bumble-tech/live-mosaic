package com.bumble.livemosaic.appyx.component.backstackclipper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.property.impl.ZIndex
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@Suppress("unused")
@MutableUiStateSpecs
class TargetUiState(
    val clipShapeProgress: ClipShapeProgress.Target,
    val zIndex: ZIndex.Target,
) {
    fun toMutableUiState(uiContext: UiContext, shape: @Composable (progress: Float) -> Shape): MutableUiState =
        MutableUiState(
            uiContext = uiContext,
            clipShapeProgress = ClipShapeProgress(
                coroutineScope = uiContext.coroutineScope,
                target = clipShapeProgress,
                shape = shape
            ),
            zIndex = ZIndex(
                coroutineScope = uiContext.coroutineScope,
                target = zIndex
            )
        )
}
