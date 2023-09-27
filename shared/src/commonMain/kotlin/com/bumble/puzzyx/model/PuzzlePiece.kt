package com.bumble.puzzyx.model

import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

@Parcelize
data class PuzzlePiece(
    val i: Int,
    val j: Int,
    val entryId: Int
) : Parcelable
