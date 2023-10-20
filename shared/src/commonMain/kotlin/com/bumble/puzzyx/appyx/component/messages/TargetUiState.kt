package com.bumble.puzzyx.appyx.component.messages

import com.bumble.appyx.interactions.core.ui.property.impl.Alpha
import com.bumble.appyx.interactions.core.ui.property.impl.RotationX
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@MutableUiStateSpecs
data class TargetUiState(
    val position: PositionInside.Target,
    val rotationX: RotationX.Target,
    val scale: Scale.Target,
    val alpha: Alpha.Target,
)
