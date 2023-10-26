package com.bumble.livemosaic.model.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumble.livemosaic.composable.scaleFactor
import com.bumble.livemosaic.ui.appyx_dark
import com.bumble.livemosaic.ui.appyx_yellow1
import com.bumble.livemosaic.ui.appyx_yellow2
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Time(val hours: Int, val minutes: Int, val seconds: Int)

@Composable
fun ClockWidget(modifier: Modifier = Modifier) {
    fun currentTime(): Time {
        val cal = Clock.System.now()
        val dateTime: LocalDateTime = cal.toLocalDateTime(TimeZone.of("Europe/London"))
        return Time(
            hours = dateTime.hour,
            minutes = dateTime.minute,
            seconds = dateTime.second,
        )
    }

    var time by remember { mutableStateOf(currentTime()) }
    LaunchedEffect(0) {
        while (true) {
            time = currentTime()
            delay(1000)
        }
    }

    Clock(time, modifier)
}

@Composable
fun Clock(time: Time, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val padding = Modifier.padding(horizontal = 2.dp * scaleFactor())

        NumberColumn(time.hours / 10, 0..2, padding)
        NumberColumn(time.hours % 10, 0..9, padding)

        Spacer(Modifier.size(8.dp * scaleFactor()))

        NumberColumn(time.minutes / 10, 0..5, padding)
        NumberColumn(time.minutes % 10, 0..9, padding)

        Spacer(Modifier.size(8.dp * scaleFactor()))

        NumberColumn(time.seconds / 10, 0..5, padding)
        NumberColumn(time.seconds % 10, 0..9, padding)
    }
}

@Composable
fun NumberColumn(
    current: Int,
    range: IntRange,
    modifier: Modifier = Modifier,
) {
    val size = 20.dp * scaleFactor()

    val mid = (range.last - range.first) / 2f
    val reset = current == range.first
    val offset by animateDpAsState(
        targetValue = size * (mid - current),
        animationSpec = if (reset) {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        } else {
            tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing,
            )
        }
    )

    val dens = LocalDensity.current.density
    Column(
        modifier
            .offset {
                IntOffset(0, (offset.value * dens).toInt())
            }
            .requiredHeight(IntrinsicSize.Max)
            .clip(RoundedCornerShape(percent = 25))
    ) {
        for (num in range) {
            Number(num == current, num, Modifier.size(size))
        }
    }
}

@Composable
fun Number(active: Boolean, value: Int, modifier: Modifier = Modifier) {
    val backgroundColor by animateColorAsState(
        if (active) appyx_yellow1 else appyx_yellow2,
    )

    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value.toString(),
            fontSize = 10.sp * scaleFactor(),
            color = appyx_dark,
        )
    }
}