package com.bumble.puzzyx.model

enum class Puzzle(
    val columns: Int,
    val rows: Int
) {

    PUZZLE1(19, 9);

    val maxEntryCount: Int = columns * rows
}
