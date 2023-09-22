package com.bumble.puzzyx.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MessageBoard(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Text(
            text = "MessageBoard",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
