package com.bumble.puzzyx.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumble.puzzyx.imageloader.EmbeddableResourceImage

@Composable
fun CallToActionScreen(
    modifier: Modifier = Modifier,
) {
    EmbeddableResourceImage(
        path = "bumble_droidcon.jpg",
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxSize()
    )
}
