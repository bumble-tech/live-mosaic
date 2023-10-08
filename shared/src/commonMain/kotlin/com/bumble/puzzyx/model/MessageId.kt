package com.bumble.puzzyx.model

import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

@Parcelize
data class MessageId(
    val entryId: Int,
) : Parcelable
