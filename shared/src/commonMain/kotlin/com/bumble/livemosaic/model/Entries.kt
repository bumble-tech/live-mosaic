package com.bumble.livemosaic.model

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC1
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC2
import com.bumble.livemosaic.model.MosaicConfig.MOSAIC3
import com.bumble.livemosaic.ui.md_indigo_500
import com.bumble.livemosaic.ui.md_lime_500

val entries = listOf(
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "codeNinja",
        message = "Having a blast at the conference!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "techWanderlust",
        message = "Keynote was inspiring"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "dataGeek",
        message = "Who's up for a post-conference karaoke session tonight? " +
                "Mind blown by the innovative ideas shared today. " +
                "Great to see old friends and make new ones!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "codeCraftsman",
        message = "Learning, networking, and free coffee – conference life is good!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "byteBender",
        message = "Who's up for a post-conference karaoke session tonight? " +
                "Mind blown by the innovative ideas shared today. " +
                "Great to see old friends and make new ones!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "stellarCoder",
        message = "L77tc0der was here"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "codeWaveSurfer",
        message = "Mind blown by the innovative ideas shared today."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "devDreamer",
        message = "Great to see old friends and make new ones!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "cyberPioneer",
        message = "Who's up for a post-conference karaoke session tonight? " +
                "Mind blown by the innovative ideas shared today. " +
                "Great to see old friends and make new ones!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "codeMaverick",
        message = "Kudos to the speakers for keeping us engaged all day."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "gitGuru",
        message = "Highlight of the day: the interactive workshop on Appyx."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "byteBlaze",
        message = "Impressed by the cool tech showcased in the exhibition hall!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "bugHuntingHero",
        message = "Taking copious notes – my brain might explode!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "algoExplorer",
        message = "Attending from NYC – making my hometown proud!"
    ),
    Entry.Image(
        mosaic = MOSAIC1,
        githubUserName = "codeWhizKid",
        path = "cake.png",
        contentScale = ContentScale.Crop
    ),
    Entry.ComposableContent(
        mosaic = MOSAIC1,
        githubUserName = "pixelPirate",
        content = {
            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = md_indigo_500,
                targetValue = md_lime_500,
                animationSpec = infiniteRepeatable(
                    animation = tween(500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            )
        }
    ),
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
        if (entries.filter { it.mosaic == MOSAIC1 }.size < MOSAIC1.maxEntryCount)
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
        if (entries.filter { it.mosaic == MOSAIC1 }.size < MOSAIC1.maxEntryCount)
            error("Mosaic 1 is not yet filled up. Add your entry to it!")
        if (entries.filter { it.mosaic == MOSAIC2 }.size < MOSAIC2.maxEntryCount)
            error("Mosaic 2 is not yet filled up. Add your entry to it!")
        if (it.map { it.githubUserName }.distinct().size < it.size) {
            error("One entry per mosaic is the limit, but you can try again in the next one!")
        }
    }

fun List<Entry>.hasMosaic2Entries() = any { it.mosaic == MOSAIC2 }

fun List<Entry>.hasMosaic3Entries() = any { it.mosaic == MOSAIC3 }
