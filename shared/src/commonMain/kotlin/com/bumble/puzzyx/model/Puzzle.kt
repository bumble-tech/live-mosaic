package com.bumble.puzzyx.model

enum class Puzzle(
    val imagesDir: String,
    val columns: Int,
    val rows: Int
) {

    PUZZLE1("bumble_logo", 19, 9);

    val maxEntryCount: Int = columns * rows
}
