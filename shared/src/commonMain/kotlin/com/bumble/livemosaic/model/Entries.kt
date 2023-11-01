package com.bumble.livemosaic.model

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.livemosaic.participant.ClockWidget
import com.bumble.livemosaic.participant.DroidconLondonHalloweenSpecial
import com.bumble.livemosaic.participant.composablesheep.BasicSheepColor
import com.bumble.livemosaic.participant.composablesheep.LoadingBasicSheep
import com.bumble.livemosaic.participant.MagicButton

val entries = listOf(
    Entry.Text(
        githubUserName = "zsoltk",
        message = "Hello Droidcon!"
    ),
    Entry.Text(
        githubUserName = "renclav",
        message = "Hello Droidcon!"
    ),
    Entry.Text(
        githubUserName = "KovalevAndrey",
        message = "Looking forward to all the talks, meeting new people and beer at the afterparty!"
    ),

    Entry.Text(
        githubUserName = "manuel-martos",
        message = "Happy to be here for another year, eager to attend the talks and meet with many folks! #dclnd23"
    ),

    Entry.Text(
        githubUserName = "sergioborne",
        message = "Last year Berlin, this year London. Where to next year?? #dcldn23"
    ),

    Entry.Text(
        githubUserName = "manuelvicnt",
        message = "Don't miss the UI layer talk on Friday at 10:15am!!!! See you at Lovelace ;)"
    ),

    Entry.Text(
        githubUserName = "Cassnyo",
        message = "It's awesome to be here for the first time! :D"
    ),
    Entry.Text(
        githubUserName = "soniammarshall",
        message = "Hello DroidCon London, from Berlin :)"
    ),

    Entry.Text(
        githubUserName = "mapm14",
        message = "Welcome back to London, folks! Excited to be here for another year"
    ),

    Entry.Text(
        githubUserName = "astamato",
        message = "Hey yall! Who's at DC LON??? :D Hoping to meet ye here!"
    ),

    Entry.Text(
        githubUserName = "emepox",
        message = "This is my first time in the London Droidcon! I'm very happy to be here!"
    ),
    Entry.Text(
        githubUserName = "freedomchuks",
        message = "Freedom was here! mama i made it"
    ),

    Entry.Text(
        githubUserName = "jlmd",
        message = "Hi mom, I'm on TV!!"
    ),

    Entry.Text(
        githubUserName = "kardelio",
        message = "Practical ADB to enhance your life talk in Hamilton on Friday at 13:25 ... You wont regret it... Or maybe you will, I dunno..."
    ),

    Entry.Text(
        githubUserName = "pedrorg18",
        message = "Hi there, happy to be here and attend interesting talks!"
    ),

    Entry.Text(
        githubUserName = "juanmazake",
        message = "You never lose. You either win or you learn."
    ),

    Entry.Text(
        githubUserName = "kamikatze2008",
        message = "Welcome to #droidconLondon. What's your favorite talk so far?"
    ),

    Entry.Text(
        githubUserName = "JorgeDLS",
        message = "I develop, Kotlin guides me. Knock knock!"
    ),

    Entry.Text(
        githubUserName = "VadymPinchuk",
        message = "System limitations boosts creativity."
    ),

    Entry.Text(
        githubUserName = "jamesdpli",
        message = "Hello Droidcon 2023 :)"
    ),

    Entry.Text(
        githubUserName = "vladcipariu91",
        message = "Another great year for Droidcon London! Can't wait to see you all there!"
    ),

    Entry.Text(
        githubUserName = "neobrahma",
        message = "Bokit is the new burger"
    ),

    Entry.Text(
        githubUserName = "Phelenor",
        message = "Greetings from Sofascore!"
    ),
    Entry.Text(
        githubUserName = "AnujVashisht",
        message = "This is the most popular entry, coming straight from phone 😅"
    ),
    Entry.Text(
        githubUserName = "alsafer",
        message = "Hello Droidcon London! Another great conference meeting great people!"
    ),
    Entry.Text(
        githubUserName = "yoni.tietz",
        message = "Hello world! Have a great day!"
    ),
    Entry.Text(
        githubUserName = "pmurph0",
        message = "Hello Droidcon! :)"
    ),
    Entry.Text(
        githubUserName = "eldoDroid8",
        message = "Happy droidDay!!!!"
    ),
    Entry.ComposableContent(
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
        githubUserName = "Aman-15",
        message = "Excited to attend my first Droidcon! Hoping to meet many of you :)"
    ),

    Entry.Image(
        githubUserName = "mike-n-jordan",
        path = "mike_avatar.png"
    ),

    Entry.Image(
        githubUserName = "esmagoksal",
        path = "esma_manel.png"
    ),

    Entry.Image(
        githubUserName = "aashay-gaikwad",
        path = "aashay-photo.png",
        contentDescription = "Bumble team"
    ),

    Entry.Text(
        githubUserName = "vassilisimon",
        message = "First time at dcldn, it is amazing here."
    ),

    Entry.Text(
        githubUserName = "jorcollmar",
        message = "For the people sound enjoying the sun!"
    ),

    Entry.Text(
        githubUserName = "osaamakhalil",
        message = "Enjoy Android life"
    ),

    Entry.Text(
        githubUserName = "petra-cendelinova",
        message = "Having a great time at my first Droidcon London!"
    ),

    Entry.Text(
        githubUserName = "ayusch",
        message = "Blimey! Great to be in Droidcon London"
    ),

    Entry.Text(
        githubUserName = "LethalMaus",
        message = "Nice to see you all here at Droidcon!"
    ),

    Entry.Text(
        githubUserName = "marin-marsic",
        message = "Roses are red, Bumble is yellow, if you're reading this, you're a nice fellow!"
    ),

    Entry.Text(
        githubUserName = "Zhelyazko",
        message = "Have fun everyone at Droidcon London!"
    ),

    Entry.Image(
        githubUserName = "di-maroo",
        path = "watching_you.jpeg",
        contentDescription = "Bumble team"
    ),

    Entry.Image(
        githubUserName = "mezentsev",
        path = "mezentsev.jpg"
    ),

    Entry.Text(
        githubUserName = "edward1432",
        message = "Best wishes from all LBG Android Devs!"
    ),

    Entry.ComposableContent(
        githubUserName = "zsmb13",
        content = {
            ClockWidget(Modifier.background(Color.DarkGray).fillMaxSize())
        }
    ),

    Entry.Text(
        githubUserName = "cortinico",
        message = "OSS is Awesome (⌐■_■)"
    ),

    Entry.Text(
        githubUserName = "marekpdev",
        message = "Great to talk to so many interesting people at Droidcon!"
    ),

    Entry.Text(
        githubUserName = "mho-bbk",
        message = "Amazing Droidcon! Very inspired by everyone here today :)"
    ),

    Entry.Text(
        githubUserName = "victor-wallapop",
        message = "Why so serious?"
    ),

    Entry.Text(
        githubUserName = "vladislavfitz",
        message = "Vive Android"
    ),

    Entry.Text(
        githubUserName = "berovikaki",
        message = "So happy to be here!"
    ),
    Entry.Text(
        githubUserName = "aallam",
        message = "Hello from Paris 🥖"
    ),

    Entry.Text(
        githubUserName = "Ecatombe",
        message = "Free Rick Sanchez!!!"
    ),

    Entry.Image(
        githubUserName = "jeremyleenz",
        path = "jeremy.jpg",
        contentDescription = "Having a great time at Droidcon London!"
    ),

    Entry.Text(
        githubUserName = "hln-h",
        message = "Droidcon London lets go! 🤙🤙"
    ),

    Entry.ComposableContent(
        githubUserName = "stewemetal",
        content = { DroidconLondonHalloweenSpecial() }
    ),
    Entry.ComposableContent(
        githubUserName = "wisors",
        content = { MagicButton() }
    ),
    Entry.Image(
        githubUserName = "Kaaveh",
        path = "kaaveh.jpg",
    ),
    Entry.Text(
        githubUserName = "Karambar",
        message = "Having amazing time at DroidCon London! 🎉🤙"
    ),
    Entry.Text(
        githubUserName = "mghisham",
        message = "Hello Bumble! excited to collect my amazon voucher:)"
    ),
    Entry.Text(
        githubUserName = "katekatjuchka",
        message = "It is an amazing conference, kudos to the organisers, Happy Friday!"
    ),

    Entry.Text(
        githubUserName = "gabrielrodriguez2746",
        message = "Bumble give me the voucher! 🤙🤙"
    ),

    Entry.Text(
        githubUserName = "AnnaMedvedieva",
        message = "Hello from Droidcon 2023!"
    ),

    Entry.Text(
        githubUserName = "rhnoriega",
        message = "Hola! from droidon in london"
    ),
    Entry.Text(
        githubUserName = "michaeltweed",
        message = "Great speaking to everyone at the booth'"
    ),
    Entry.Text(
        githubUserName = "arj154",
        message = "Happy Friday"
    ),

    Entry.Text(
        githubUserName = "oheyadam",
        message = "Zsolt made me do it"
    ),
    Entry.Text(
        githubUserName = "ericdecanini",
        message = "Mike is a legend (he didn't make me say this)"
    ),
    Entry.Text(
        githubUserName = "VladislavAlfredov",
        message = "Woot! Hi at DroidCon!"
    ),
    Entry.Text(
        githubUserName = "gaelmarhic",
        message = "Make the first move!"
    ),
    Entry.Text(
        githubUserName = "JuliaSotola",
        message = "Make the world a better place!"
    ),
    Entry.ComposableContent(
        githubUserName = "nicole-terc",
        content = {
            val fluffColor = BasicSheepColor.random()
            val backgroundColor = BasicSheepColor.random(fluffColor)
            LoadingBasicSheep(
                fluffColor = fluffColor,
                modifier = Modifier.fillMaxSize().background(backgroundColor),
            )
        }
    ),
    Entry.Text(
        githubUserName = "KCL-SAK",
        message = "Vernoica, Helen, Faisa and Rosie are amazing!"
    ),
    Entry.Text(
        githubUserName = "battagliandrea",
        message = "Houston we have a problem"
    ),
    Entry.Text(
        githubUserName = "MovementSpeed",
        message = "I am that italian guy"
    ),
    Entry.Text(
        githubUserName = "bibinjacob",
        message = "Having amazing time at DroidCon...."
    ),

    Entry.Text(
        githubUserName = "vyguera",
        message = "3, 2, 1: Code!"
    ),
    Entry.Text(
        githubUserName = "chor0",
        message = "let's do it: Code!"
    ),
    Entry.Text(
        githubUserName = "kiwi4747",
        message = "Mad as a banana"
    ),
    Entry.Text(
        githubUserName = "iNoles",
        message = "Where code meets the cosmos, we find infinite possibilities. 🌌✨🚀 #KotlinSpaceCoast"
    ),
)

