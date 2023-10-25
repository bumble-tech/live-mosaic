package com.bumble.livemosaic.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumble.livemosaic.imageloader.EmbeddableResourceImage

@Composable
fun CallToActionScreen(
    modifier: Modifier = Modifier,
) {
    EmbeddableResourceImage(
        path = "bumble/mosaic/bumble_droidcon.jpg",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxSize()
    )
}
