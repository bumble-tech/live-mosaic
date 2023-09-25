package com.bumble.puzzyx.model

data class Entry(
    val githubUserName: String = fakeGitHubUserNames.random(),
    val twitterHandle: String = fakeTwitterHandles.random(),
    val message: String = fakeMessages.random()
)
