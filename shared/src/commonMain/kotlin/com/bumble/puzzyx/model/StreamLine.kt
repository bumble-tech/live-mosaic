package com.bumble.puzzyx.model

import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

@Parcelize
data class StreamLine(
    val rotation: Float,
    val entryIds: List<Int>,
) : Parcelable
