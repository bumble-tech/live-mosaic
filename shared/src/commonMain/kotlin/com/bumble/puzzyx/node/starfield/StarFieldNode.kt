package com.bumble.puzzyx.node.starfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.collections.immutableListOf
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.puzzyx.composable.AutoPlayScript
import com.bumble.puzzyx.composable.StarFieldMessageBoard
import com.bumble.puzzyx.data.EntryDataSource
import com.bumble.puzzyx.model.Entry

class StarFieldNode(
    buildContext: BuildContext,
    private val entryDataSource: EntryDataSource,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var entriesForStarField by remember { mutableStateOf(immutableListOf<Entry>()) }
        AutoPlayScript(initialDelayMs = 20000) { finish() }
        LaunchedEffect(Unit) {
            entriesForStarField = entryDataSource.getFeaturedEntries(
                entriesCount = 20,
                newestEntriesCount = 12,
            )
        }
        if (entriesForStarField.isNotEmpty()) {
            StarFieldMessageBoard(
                entries = entriesForStarField,
                modifier = modifier,
            )
        }
    }
}
