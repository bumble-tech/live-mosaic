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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "manuel-martos",
        message = "Happy to be here for another year, eager to attend the talks and meet with many folks! #dclnd23"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "sergioborne",
        message = "Last year Berlin, this year London. Where to next year?? #dcldn23"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "manuelvicnt",
        message = "Don't miss the UI layer talk on Friday at 10:15am!!!! See you at Lovelace ;)"
    ),    
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "Cassnyo",
        message = "It's awesome to be here for the first time! :D"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "soniammarshall",
        message = "Hello DroidCon London, from Berlin :)"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "mapm14",
        message = "Welcome back to London, folks! Excited to be here for another year"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "astamato",
        message = "Hey yall! Who's at DC LON??? :D Hoping to meet ye here!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "emepox",
        message = "This is my first time in the London Droidcon! I'm very happy to be here!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "jlmd",
        message = "Hi mom, I'm on TV!!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "kardelio",
        message = "Practical ADB to enhance your life talk in Hamilton on Friday at 13:25 ... You wont regret it... Or maybe you will, I dunno..."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "pedrorg18",
        message = "Hi there, happy to be here and attend interesting talks!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "juanmazake",
        message = "You never lose. You either win or you learn."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "kamikatze2008",
        message = "Welcome to #droidconLondon. What's your favorite talk so far?"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "VadymPinchuk",
        message = "System limitations boosts creativity."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "jamesdpli",
        message = "Hello Droidcon 2023 :)"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "vladcipariu91",
        message = "Another great year for Droidcon London! Can't wait to see you all there!"
    ),
    Entry.ComposableContent(
        mosaic = MOSAIC1,
        githubUserName = "adriantache",
        content = {
            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = Color(0xffffcb37),
                targetValue = Color(0xffa86807),
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(color, RoundedCornerShape(16.dp))
            ) {
                Text(
                    "Great vibe here at droidcon London!",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "Aman-15",
        message = "Excited to attend my first Droidcon! Hoping to meet many of you :)"
    ),
    Entry.Image(
        mosaic = MOSAIC1,
        githubUserName = "mike-n-jordan",
        path = "mike_avatar.png"
    ),
    Entry.Image(
        mosaic = MOSAIC1,
        githubUserName = "esmagoksal",
        path = "esma_manel.png"
    ),
    Entry.Image(
        mosaic = MOSAIC1,
        githubUserName = "aashay-gaikwad",
        path = "aashay-photo.png",
        contentDescription = "Bumble team"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "vassilisimon",
        message = "First time at dcldn, it is amazing here."
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "jorcollmar",
        message = "For the people sound enjoying the sun!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "osaamakhalil",
        message = "Enjoy Android life"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "petra-cendelinova",
        message = "Having a great time at my first Droidcon London!"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "ayusch",
        message = "Blimey! Great to be in Droidcon London"
    ),
    Entry.Text(
        mosaic = MOSAIC1,
        githubUserName = "LethalMaus",
        message = "Nice to see you all here at Droidcon!"
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
