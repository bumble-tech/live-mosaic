package com.bumble.puzzyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.ui.property.impl.RotationY
import com.bumble.appyx.interactions.core.ui.property.impl.RotationZ
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@MutableUiStateSpecs
class TargetUiState(
    val position: PositionInside.Target,
    val rotationZ: RotationZ.Target = RotationZ.Target(0f),
    val rotationY: RotationY.Target = RotationY.Target(0f)
//    val width: Width.Target,
//    val height: Height.Target
)
