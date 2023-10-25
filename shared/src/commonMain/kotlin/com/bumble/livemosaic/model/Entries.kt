package com.bumble.livemosaic.model

import com.bumble.livemosaic.model.MosaicConfig.MOSAIC1
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC2
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC3

val entries = listOf(
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "zsoltk",
        message = "Hello Droidcon!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "KovalevAndrey",
        message = "Looking forward to all the talks, meeting new people and beer at the afterparty!"
    )
)

val mosaic1Entries = entries
    .filter { it.mosaic == MOSAIC1 }
    .also {
        if (it.size > MOSAIC1.maxEntryCount)
            error("Mosaic 1 is already filled up. Add your entry to another one!")
        if (it.map { it.githubUserName }.distinct().size < it.size) {
            error("One entry per mosaic is the limit, but you can try again in the next one!")
        }
    }

val mosaic2Entries = entries
    .filter { it.mosaic == MOSAIC2 }
    .also {
        if (it.size > MOSAIC2.maxEntryCount)
            error("Mosaic 2 is already filled up. Add your entry to another one!")
        if (it.isNotEmpty() && entries.filter { it.mosaic == MOSAIC1 }.size < MOSAIC1.maxEntryCount)
            error("Mosaic 1 is not yet filled up. Add your entry to it!")
        if (it.map { it.githubUserName }.distinct().size < it.size) {
            error("One entry per mosaic is the limit, but you can try again in the next one!")
        }
    }

val mosaic3Entries = entries
    .filter { it.mosaic == MOSAIC3 }
    .also {
        if (it.size > MOSAIC3.maxEntryCount)
            error("Mosaic 3 is already filled up. Add your entry to another one!")
        if (it.isNotEmpty() && entries.filter { it.mosaic == MOSAIC1 }.size < MOSAIC1.maxEntryCount)
            error("Mosaic 1 is not yet filled up. Add your entry to it!")
        if (it.isNotEmpty() && entries.filter { it.mosaic == MOSAIC2 }.size < MOSAIC2.maxEntryCount)
            error("Mosaic 2 is not yet filled up. Add your entry to it!")
        if (it.map { it.githubUserName }.distinct().size < it.size) {
            error("One entry per mosaic is the limit, but you can try again in the next one!")
        }
    }

fun List<Entry>.hasMosaic2Entries() = any { it.mosaic == MOSAIC2 }

fun List<Entry>.hasMosaic3Entries() = any { it.mosaic == MOSAIC3 }
