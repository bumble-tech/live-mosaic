package com.bumble.livemosaic.model

import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList

fun List<Entry>.getFeaturedEntries(
    entriesCount: Int,
    newestEntriesCount: Int
): ImmutableList<Entry> {
    require(entriesCount >= newestEntriesCount)
    val newestEntries = this
        .takeLast(newestEntriesCount)
        .reversed()

    val remainingEntries = this
        .dropLast(newestEntriesCount)
        .shuffled()
        .take(entriesCount - newestEntriesCount)
    val temporaryEntries = newestEntries + remainingEntries
    return if (temporaryEntries.size < entriesCount) {
        val repeatEntries = this.shuffled().take(entriesCount - temporaryEntries.size)
        (newestEntries + remainingEntries + repeatEntries).toImmutableList()
    } else {
        (newestEntries + remainingEntries).toImmutableList()
    }
}
