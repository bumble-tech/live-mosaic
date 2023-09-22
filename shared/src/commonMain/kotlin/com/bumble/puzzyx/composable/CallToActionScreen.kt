package com.bumble.puzzyx.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.puzzyx.ui.appyx_dark
import com.bumble.puzzyx.ui.appyx_yellow1

@Composable
fun CallToActionScreen(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(appyx_dark)
    ) {
        Text(
            text = "Join the challenge",
            color = appyx_yellow1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
