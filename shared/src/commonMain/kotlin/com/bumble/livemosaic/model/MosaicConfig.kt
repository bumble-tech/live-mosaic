package com.bumble.livemosaic.model

enum class MosaicConfig(
    val frontImagesDir: String,
    val backImagesDir: String,
    val columns: Int,
    val rows: Int
) {

    MOSAIC1("mosaic/1_front", "mosaic/1_back",12, 7),
    MOSAIC2("mosaic/2_front", "mosaic/2_back", 12, 7),
    MOSAIC3("mosaic/3_front", "mosaic/3_back",12, 7);


    val maxEntryCount: Int = columns * rows
}
