package com.bumble.puzzyx.data

import com.bumble.appyx.navigation.collections.ImmutableList
import com.bumble.appyx.navigation.collections.toImmutableList
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.model.entries

interface EntryDataSource {
    suspend fun getFeaturedEntries(entriesCount: Int, newestEntriesCount: Int): ImmutableList<Entry>
}

class EntryDataSourceImpl : EntryDataSource {
    override suspend fun getFeaturedEntries(entriesCount: Int, newestEntriesCount: Int): ImmutableList<Entry> {
        val newestEntries = entries
            .takeLast(newestEntriesCount)
            .reversed()
            
        val remainingEntries = entries
            .dropLast(newestEntriesCount)
            .shuffled()
            .take(entriesCount - newestEntriesCount)
        val temporaryEntries = newestEntries + remainingEntries
        return if (temporaryEntries.size < entriesCount) {
            val repeatEntries = entries.shuffled().take(entriesCount - temporaryEntries.size)
            (newestEntries + remainingEntries + repeatEntries).toImmutableList()
        } else {
            (newestEntries + remainingEntries).toImmutableList()
        }
    }
}
