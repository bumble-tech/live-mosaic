package com.bumble.livemosaic.model

import com.bumble.livemosaic.model.MosaicConfig.MOSAIC1

val entries = listOf(
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "zsoltk",
        message = "Hello Droidcon!"
    ),
)

val mosaic1Entries = entries
    .filter { it.mosaic == MOSAIC1 }
    .also {
        if (it.size > MOSAIC1.maxEntryCount)
            error("This mosaic is already filled up. Add your entry to another one!")

        if (it.map { it.githubUserName }.distinct().size < it.size) {
            error("One entry per mosaic is the limit, but you can try again in the next one!")
        }
    }
