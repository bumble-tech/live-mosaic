package com.bumble.puzzyx.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.bumble.puzzyx.ui.LocalAutoPlayFlow
import kotlinx.coroutines.delay

@Composable
fun AutoPlayScript(
    initialDelayMs: Long = 1,
    retryDelayMs: Long = 3000,
    steps: List<Pair<() -> Unit, Long>>
) {
    val autoPlay = LocalAutoPlayFlow.current
    LaunchedEffect(Unit) {
        delay(initialDelayMs)
        while (true) {
            steps.forEach { (action, delayMs) ->
                if (autoPlay.value) action()
                if (autoPlay.value) delay(delayMs)
                while (!autoPlay.value) { delay(retryDelayMs) }
            }
        }
    }
}
