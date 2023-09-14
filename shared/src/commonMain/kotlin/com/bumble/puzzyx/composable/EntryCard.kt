package com.bumble.puzzyx.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.puzzyx.entries.Entry

@Composable
fun EntryCard(
    modifier: Modifier,
    entry: Entry
) {
    ScaledLayout(
        modifier = modifier.padding(8.dp),
        scale = 0.5f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(entry.githubUserName)
            Text(entry.twitterHandle)
            Text(entry.message)
        }
    }
}
