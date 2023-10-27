package com.bumble.livemosaic.node.starfield

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
import com.bumble.livemosaic.composable.AutoPlayScript
import com.bumble.livemosaic.composable.StarFieldMessageBoard
import com.bumble.livemosaic.model.Entry
import com.bumble.livemosaic.model.entries
import com.bumble.livemosaic.model.getFeaturedEntries

class StarFieldNode(
    buildContext: BuildContext,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        var entriesForStarField by remember { mutableStateOf(immutableListOf<Entry>()) }
        AutoPlayScript(initialDelayMs = 30000) { finish() }
        LaunchedEffect(Unit) {
            entriesForStarField = entries.getFeaturedEntries(
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
