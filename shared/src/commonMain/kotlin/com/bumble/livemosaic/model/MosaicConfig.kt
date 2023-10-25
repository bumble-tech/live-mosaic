package com.bumble.livemosaic.model

enum class MosaicConfig(
    val frontImagesDir: String,
    val backImagesDir: String,
    val columns: Int,
    val rows: Int
) {

    MOSAIC1("1_front", "1_back",12, 7),
    MOSAIC2("2_front", "2_back", 12, 7),
    MOSAIC3("3_front", "3_back",12, 7);


    val maxEntryCount: Int = columns * rows
}
