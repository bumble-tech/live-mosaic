package com.bumble.livemosaic.composable

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage
import com.bumble.appyx.navigation.integration.LocalScreenSize
import com.bumble.appyx.navigation.integration.ScreenSize.WindowSizeClass
import com.bumble.livemosaic.model.Entry
import com.bumble.livemosaic.ui.colors
import kotlinx.coroutines.isActive

@Composable
fun EntryCard(
    entry: Entry,
    modifier: Modifier = Modifier,
) {
    val scaleFactor = scaleFactor()
    val size = 24.dp * scaleFactor
    Box(
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    ) {
        when (entry) {
            is Entry.Text -> TextEntry(
                entry = entry,
                paddingTop = size
            )

            is Entry.Image -> EmbeddableResourceImage(
                path = "participant/${entry.path}",
                contentDescription = entry.contentDescription,
                contentScale = entry.contentScale,
                modifier = Modifier.fillMaxSize().align(Alignment.Center)
            )

            is Entry.ComposableContent -> entry.content()
        }
        GitHubHeader(
            size = size,
            entry = entry,
            modifier = Modifier.padding(8.dp * scaleFactor)
        )
    }
}

@Composable
fun GitHubHeader(
    size: Dp,
    entry: Entry,
    modifier: Modifier = Modifier
) {
    val scaleFactor = scaleFactor()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        EmbeddableResourceImage(
            path = "github.png",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .padding(2.dp)
                .size(size)
        )
        Spacer(
            modifier = Modifier.size(4.dp * scaleFactor)
        )
        Text(
            text = entry.githubUserName,
            fontSize = 16.sp * scaleFactor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TextEntry(
    paddingTop: Dp,
    entry: Entry.Text,
    modifier: Modifier = Modifier
) {
    val colorIdx = remember { colors.indices.random() }
    val state = rememberScrollState()

    LaunchedEffect(Unit) {
        while (isActive) {
            state.animateScrollTo(state.maxValue, tween(10000))
            state.scrollTo(0)
        }
    }

    val scaleFactor = scaleFactor()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors[colorIdx])
            .padding(12.dp * scaleFactor)
            .padding(top = paddingTop)
            .clipToBounds()
            .verticalScroll(state)
    ) {
        Text(
            text = entry.message,
            fontSize = 16.sp * scaleFactor,
        )
    }
}

@Composable
fun scaleFactor(): Float = when (LocalScreenSize.current.windowSizeClass) {
    WindowSizeClass.COMPACT -> 1f
    WindowSizeClass.MEDIUM -> 1.5f
    WindowSizeClass.EXPANDED -> 2f
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
