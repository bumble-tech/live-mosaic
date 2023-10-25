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
        /**
         * - Should not be larger than 500 KB
         * - Should not be larger than 600 x 400px
         * - Be sure to add it to `/shared/commonMain/resources/participant`
         * - Set `path` to just the filename (without the directory name)
         * - For the best looks, have it in landscape mode with 3:2 aspect ratio
         */
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
