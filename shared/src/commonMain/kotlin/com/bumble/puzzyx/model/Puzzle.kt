package com.bumble.puzzyx.model

import androidx.compose.runtime.Immutable

@Immutable
enum class Puzzle(
    val imagesDir: String,
    val columns: Int,
    val rows: Int
) {

    PUZZLE1("bumble_logo", 19, 9);

    val maxEntryCount: Int = columns * rows
}
