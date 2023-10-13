package com.bumble.puzzyx.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.puzzyx.imageloader.ResourceImage
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.ui.colors

@Composable
fun EntryCard(
    entry: Entry,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    ) {
        when (entry) {
            is Entry.Text -> TextEntry(entry)
            is Entry.Image -> ResourceImage(
                path = "participant/${entry.path}",
                contentDescription = entry.contentDescription,
                contentScale = entry.contentScale,
                modifier = Modifier.fillMaxSize().align(Alignment.Center)
            )

            is Entry.ComposableContent -> entry.content()
        }
        GitHubHeader(
            entry = entry,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GitHubHeader(
    entry: Entry,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ResourceImage(
            path = "github.png",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(32.dp)
                .padding(2.dp)
        )
        Spacer(
            modifier = Modifier.size(4.dp)
        )
        Text(
            text = entry.githubUserName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TextEntry(
    entry: Entry.Text,
    modifier: Modifier = Modifier
) {
    val colorIdx = remember { colors.indices.random() }

    Text(
        text = entry.message,
        fontSize = 16.sp,
        modifier = modifier
            .fillMaxSize()
            .background(colors[colorIdx])
            .padding(12.dp)
            .padding(top = 36.dp),
    )
}

@Composable
fun EntryCardSmall(
    entry: Entry.Text,
    modifier: Modifier = Modifier,
) {
    OptimisingLayout(
        modifier = modifier,
        optimalWidth = 150.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(entry.githubUserName)
            Text(entry.message)
        }
    }
}
