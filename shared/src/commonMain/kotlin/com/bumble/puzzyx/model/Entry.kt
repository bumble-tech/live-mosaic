package com.bumble.puzzyx.model

data class Entry(
    val puzzle: Puzzle,
    val githubUserName: String = fakeGitHubUserNames.random(),
    val message: String = fakeMessages.random()
)
