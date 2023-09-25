package com.bumble.puzzyx.model

val entries = listOf(
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "codeNinja",
        message = "Having a blast at the conference!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "techWanderlust",
        message = "Keynote was inspiring"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "dataGeek",
        message = "Shoutout to the organizers for an amazing lineup!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "codeCraftsman",
        message = "Learning, networking, and free coffee – conference life is good!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "byteBender",
        message = "Let's connect! Find me at the networking session!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "stellarCoder",
        message = "L77tc0der was here"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "codeWaveSurfer",
        message = "Mind blown by the innovative ideas shared today."
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "devDreamer",
        message = "Great to see old friends and make new ones!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "cyberPioneer",
        message = "Who's up for a post-conference karaoke session tonight?"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "codeMaverick",
        message = "Kudos to the speakers for keeping us engaged all day."
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "gitGuru",
        message = "Highlight of the day: the interactive workshop on Appyx."
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "byteBlaze",
        message = "Impressed by the cool tech showcased in the exhibition hall!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "bugHuntingHero",
        message = "Taking copious notes – my brain might explode!"
    ),
    Entry(
        puzzle = Puzzle.PUZZLE1,
        githubUserName = "bugHuntingHero",
        message = "Attending from NYC – making my hometown proud!"
    ),
)

val puzzle1Entries = entries.filter { it.puzzle == Puzzle.PUZZLE1 }
