package com.bumble.livemosaic.participant

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import kotlin.random.Random

@Composable
fun MagicButton() {
    // Generate random colors
    fun randomColor(): Color = Color(
        red = Random.nextInt(256),
        green = Random.nextInt(256),
        blue = Random.nextInt(256)
    )

    var bgColor by remember { mutableStateOf(randomColor()) }
    var textColor by remember { mutableStateOf(randomColor()) }

    // Animate color transition
    val animatedBgColor by animateColorAsState(targetValue = bgColor, animationSpec = tween(1000))
    val animatedTextColor by animateColorAsState(targetValue = textColor, animationSpec = tween(1000))

    LaunchedEffect(Unit) {
        // Change color every 1 second
        while (true) {
            delay(1000) // wait for 1 second
            bgColor = randomColor()
            textColor = randomColor()
        }
    }

    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .background(animatedBgColor)
            .clickable {
                // Redirect to the website
                uriHandler.openUri("https://bumble-tech.github.io/appyx/")
            }
            .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Click me!",
            color = animatedTextColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.alpha(1f)
        )
    }
}