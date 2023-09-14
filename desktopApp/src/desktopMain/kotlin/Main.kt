import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.bumble.appyx.navigation.integration.DesktopNodeHost
import com.bumble.puzzyx.node.app.PuzzyxAppNode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class Events {
    object OnBackPressed : Events()
}

fun main() = application {
    val events: Channel<Events> = Channel()
    val windowState = rememberWindowState(placement = WindowPlacement.Fullscreen)
    val eventScope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        onKeyEvent = { onKeyEvent(it, events, eventScope) },
    ) {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                DesktopNodeHost(
                    windowState = windowState,
                    onBackPressedEvents = events.receiveAsFlow().mapNotNull {
                        if (it is Events.OnBackPressed) Unit else null
                    }
                ) { buildContext ->
                    PuzzyxAppNode(
                        buildContext = buildContext,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun onKeyEvent(
    keyEvent: KeyEvent,
    events: Channel<Events>,
    coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
): Boolean =
    when {
        keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace -> {
            coroutineScope.launch { events.send(Events.OnBackPressed) }
            true
        }

        else -> false
    }
