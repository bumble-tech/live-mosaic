package com.bumble.livemosaic.model

enum class MosaicConfig(
    val frontImagesDir: String,
    val backImagesDir: String,
    val columns: Int,
    val rows: Int
) {

    MOSAIC1("bumble/mosaic/1_front", "bumble/mosaic/1_back",12, 7),
    MOSAIC2("bumble/mosaic/2_front", "bumble/mosaic/2_back", 12, 7),
    MOSAIC3("bumble/mosaic/3_front", "bumble/mosaic/3_back",12, 7);


    val maxEntryCount: Int = columns * rows
}
