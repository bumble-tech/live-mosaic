@file:Suppress("MagicNumber")
package com.bumble.livemosaic.participant

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
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
    val animatedTextColor by animateColorAsState(
        targetValue = textColor,
        animationSpec = tween(1000)
    )
    LaunchedEffect(Unit) {
        // Change color every 1 second
        while (true) {
            delay(1000) // wait for 1 second
            bgColor = randomColor()
            textColor = randomColor()
        }
    }
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .clip(shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp))
                .background(animatedBgColor)
                .clickable {
                    // Redirect to the website
                    uriHandler.openUri("https://bumble-tech.github.io/appyx/")
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click me!",
                color = animatedTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp).alpha(1f).align(Alignment.Center)
            )
        }

    }
}