package com.bumble.puzzyx.appyx.component.stream

import com.bumble.appyx.interactions.core.ui.property.impl.Alpha
import com.bumble.appyx.interactions.core.ui.property.impl.RotationZ
import com.bumble.appyx.interactions.core.ui.property.impl.Scale
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@MutableUiStateSpecs
data class TargetUiState(
    val rotationZ: RotationZ.Target,
    val position: PositionInside.Target,
    val scale: Scale.Target,
    val alpha: Alpha.Target,
)
