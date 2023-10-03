package com.bumble.puzzyx.model

val entries = Array<Entry>(5) {
    Entry.Text(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "userId #$it",
        message = "Having a blast at the conference! $it"
    )
}.toList()

//val entries = listOf(
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "codeNinja",
//        message = "Having a blast at the conference!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "techWanderlust",
//        message = "Keynote was inspiring"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "dataGeek",
//        message = "Shoutout to the organizers for an amazing lineup!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "codeCraftsman",
//        message = "Learning, networking, and free coffee – conference life is good!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "byteBender",
//        message = "Let's connect! Find me at the networking session!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "stellarCoder",
//        message = "L77tc0der was here"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "codeWaveSurfer",
//        message = "Mind blown by the innovative ideas shared today."
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "devDreamer",
//        message = "Great to see old friends and make new ones!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "cyberPioneer",
//        message = "Who's up for a post-conference karaoke session tonight?"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "codeMaverick",
//        message = "Kudos to the speakers for keeping us engaged all day."
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "gitGuru",
//        message = "Highlight of the day: the interactive workshop on Appyx."
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "byteBlaze",
//        message = "Impressed by the cool tech showcased in the exhibition hall!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "bugHuntingHero",
//        message = "Taking copious notes – my brain might explode!"
//    ),
//    Entry.Text(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "algoExplorer",
//        message = "Attending from NYC – making my hometown proud!"
//    ),
//    Entry.Image(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "codeWhizKid",
//        path = "cake.png",
//        contentScale = ContentScale.Crop
//    ),
//    Entry.ComposableContent(
//        puzzle = Puzzle.PUZZLE1,
//        githubUserName = "pixelPirate",
//        content = {
//            val infiniteTransition = rememberInfiniteTransition()
//            val color by infiniteTransition.animateColor(
//                initialValue = md_indigo_500,
//                targetValue = md_lime_500,
//                animationSpec = infiniteRepeatable(
//                    animation = tween(500, easing = LinearEasing),
//                    repeatMode = RepeatMode.Reverse
//                )
//            )
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(color)
//            )
//        }
//    )
//)

val puzzle1Entries = entries
    .filter { it.puzzle == Puzzle.PUZZLE1 }
//    .also {
//        if (it.size > Puzzle.PUZZLE1.maxEntryCount)
//            error("This puzzle is already filled up. Add your entry to another one!")
//
//        if (it.map { it.githubUserName }.distinct().size < it.size) {
//            error("One entry per puzzle is the limit, but you can try again in the next one!")
//        }
//    }
