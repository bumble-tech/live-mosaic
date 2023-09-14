package com.bumble.puzzyx.entries

data class Entry(
    val githubUserName: String = fakeGitHubUserNames.random(),
    val twitterHandle: String = fakeTwitterHandles.random(),
    val message: String = fakeMessages.random()
)
