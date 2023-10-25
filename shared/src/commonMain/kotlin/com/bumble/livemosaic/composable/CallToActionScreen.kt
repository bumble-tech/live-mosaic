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
        path = "bumble/bumble_droidcon.png",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxSize()
    )
}
