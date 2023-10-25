package com.bumble.livemosaic.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale

sealed class Entry {
    abstract val mosaic: MosaicConfig
    abstract val githubUserName: String

    data class Text(
        override val mosaic: MosaicConfig,
        override val githubUserName: String,
        val message: String
    ) : Entry()

    data class Image(
        override val mosaic: MosaicConfig,
        override val githubUserName: String,
        val path: String,
        val contentDescription: String? = null,
        val contentScale: ContentScale = ContentScale.Fit
    ) : Entry()

    data class ComposableContent(
        override val mosaic: MosaicConfig,
        override val githubUserName: String,
        val content: @Composable () -> Unit
    ) : Entry()
}
