package com.bumble.puzzyx.math

import com.bumble.appyx.interactions.core.ui.math.lerpFloat


fun mapValueRange(
    value: Float,
    fromRangeMin: Float,
    fromRangeMax: Float,
    destRangeMin: Float,
    destRangeMax: Float
): Float =
    lerpFloat(
        start = destRangeMin,
        end = destRangeMax,
        norm(
            value = value.coerceIn(fromRangeMin, fromRangeMax),
            min = fromRangeMin,
            max = fromRangeMax
        )
    )

fun norm(
    value: Float,
    min: Float,
    max: Float
): Float =
    (value - min) / (max - min)
