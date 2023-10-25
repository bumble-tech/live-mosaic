package com.bumble.livemosaic.imageloader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
actual fun EmbeddableResourceImage(
    path: String,
    modifier: Modifier,
    contentDescription: String?,
    contentScale: ContentScale
) {
    ResourceImage(
        path = path,
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = contentDescription,
    )
}
