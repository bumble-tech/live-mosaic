package com.bumble.puzzyx.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import com.bumble.puzzyx.ui.LocalAutoPlayFlow
import kotlinx.coroutines.delay

@Composable
@NonRestartableComposable
fun AutoPlayScript(
    steps: List<Pair<() -> Unit, Long>> = emptyList(),
    initialDelayMs: Long = 1,
    retryDelayMs: Long = 3000,
    repeatSteps: Int = 1,
    onFinish: () -> Unit = {}
) {
    val autoPlay = LocalAutoPlayFlow.current
    LaunchedEffect(Unit) {
        delay(initialDelayMs)
        for (i in 1..repeatSteps) {
            steps.forEach { (action, delayMs) ->
                if (autoPlay.value) action()
                if (autoPlay.value) delay(delayMs)
                while (!autoPlay.value) { delay(retryDelayMs) }
            }
        }
        while (!autoPlay.value) { delay(retryDelayMs) }
        onFinish()
    }
}
