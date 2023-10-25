package com.bumble.livemosaic.appyx.component.backstackclipper

import androidx.compose.animation.core.SpringSpec
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.interactions.core.ui.context.UiContext
import com.bumble.appyx.interactions.core.ui.helper.DefaultAnimationSpec
import com.bumble.appyx.interactions.core.ui.property.impl.ZIndex
import com.bumble.appyx.interactions.core.ui.state.MatchedTargetUiState
import com.bumble.appyx.transitionmodel.BaseVisualisation


/**
 * With Appyx, we usually map model states (like a back stack element's state) to visual end
 * states.
 *
 * To achieve the canvas clipping effect, instead of usual Alpha, Scale, Rotation etc. properties
 * we'll animate the progress value related to a shape, and apply it as a clip mask on the outgoing
 * element.
 *
 * @param shape Should return a Shape given a progress value in the range of 0..1f. The shape will
 *              be applied as a clip mask on the outgoing back stack element.
 */
class BackStackClipper<InteractionTarget : Any>(
    uiContext: UiContext,
    private val shape: @Composable (progress: Float) -> Shape,
    defaultAnimationSpec: SpringSpec<Float> = DefaultAnimationSpec
) : BaseVisualisation<InteractionTarget, BackStackModel.State<InteractionTarget>, MutableUiState, TargetUiState>(
    uiContext = uiContext,
    defaultAnimationSpec = defaultAnimationSpec,
) {

    private val incoming = TargetUiState(
        clipShapeProgress = ClipShapeProgress.Target(0f),
        zIndex = ZIndex.Target(0f),
    )

    /**
     * The Shape is animated towards 100% progress.
     * zIndex ensures the outgoing element stays on top. As the clipping is applied to it,
     * any elements behind it should start showing through.
     */
    private val outgoing = TargetUiState(
        clipShapeProgress = ClipShapeProgress.Target(1f),
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
        targetUiState.toMutableUiState(uiContext, shape)
}
