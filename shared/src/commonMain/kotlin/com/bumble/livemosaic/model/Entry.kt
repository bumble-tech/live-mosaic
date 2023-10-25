package com.bumble.livemosaic.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale

sealed class Entry {
    abstract val puzzle: Puzzle
    abstract val githubUserName: String

    data class Text(
        override val puzzle: Puzzle,
        override val githubUserName: String,
        val message: String = fakeMessages.random()
    ) : Entry()

    data class Image(
        override val puzzle: Puzzle,
        override val githubUserName: String,
        val path: String,
        val contentDescription: String? = null,
        val contentScale: ContentScale = ContentScale.Fit
    ) : Entry()

    data class ComposableContent(
        override val puzzle: Puzzle,
        override val githubUserName: String,
        val content: @Composable () -> Unit
    ) : Entry()
}
