package com.bumble.puzzyx.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.imageloader.ResourceImage

@Composable
fun EntryCard(
    modifier: Modifier,
    entry: Entry
) {
    ScaledLayout(
        modifier = modifier.padding(12.dp),
        scale = 0.5f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ResourceImage(
                    path = "github.png",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(4.dp)
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Text(
                    text = entry.githubUserName,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            Text(
                text = entry.message,
                fontSize = 32.sp,
            )
        }
    }
}

@Composable
fun EntryCardSmall(
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
