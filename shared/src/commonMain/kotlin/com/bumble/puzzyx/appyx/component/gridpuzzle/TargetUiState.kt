package com.bumble.puzzyx.appyx.component.gridpuzzle

import com.bumble.appyx.interactions.core.ui.property.impl.AngularPosition
import com.bumble.appyx.interactions.core.ui.property.impl.RotationY
import com.bumble.appyx.interactions.core.ui.property.impl.RotationZ
import com.bumble.appyx.interactions.core.ui.property.impl.RoundedCorners
import com.bumble.appyx.interactions.core.ui.property.impl.position.PositionInside
import com.bumble.appyx.interactions.core.ui.state.MutableUiStateSpecs

@MutableUiStateSpecs
data class TargetUiState(
    val position: PositionInside.Target,
    val angularPosition: AngularPosition.Target = AngularPosition.Target(
        AngularPosition.Value.Zero
    ),
    val rotationZ: RotationZ.Target = RotationZ.Target(0f),
    val rotationY: RotationY.Target = RotationY.Target(0f),
    val roundedCorners: RoundedCorners.Target = RoundedCorners.Target(0)
)
