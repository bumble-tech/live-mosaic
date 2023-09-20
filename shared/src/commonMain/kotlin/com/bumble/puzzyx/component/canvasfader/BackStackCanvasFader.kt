package com.bumble.puzzyx.component.canvasfader

import androidx.compose.animation.core.SpringSpec
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.interactions.core.ui.property.impl.ZIndex
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs
import com.bumble.appyx.transitionmodel.BaseMotionController
import com.bumble.puzzyx.component.canvasfader.BackStackCanvasFader.TargetUiState

class BackStackCanvasFader<InteractionTarget : Any>(
    uiContext: UiContext,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseMotionController<InteractionTarget, BackStackModel.State<InteractionTarget>, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec,
) {
    @Suppress("unused")
    @MutableUiStateSpecs
    class TargetUiState(
        val canvasProgress: CanvasProgress.Target,
        val zIndex: ZIndex.Target,
    )

    private val incoming = TargetUiState(
        canvasProgress = CanvasProgress.Target(0f),
        zIndex = ZIndex.Target(0f),
    )

    private val outgoing = TargetUiState(
        canvasProgress = CanvasProgress.Target(1f),
        zIndex = ZIndex.Target(1f),
    )

    override fun BackStackModel.State<InteractionTarget>.toUiTargets():
            List<MatchedTargetUiState<InteractionTarget, TargetUiState>> =
        (created + listOf(active)).map {
            MatchedTargetUiState(it, incoming)
        } + (stashed + destroyed).map {
            MatchedTargetUiState(it, outgoing)
        }

    override fun mutableUiStateFor(uiContext: UiContext, targetUiState: TargetUiState): MutableUiState =
        targetUiState.toMutableState(uiContext)
}
