import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.bumble.appyx.navigation.integration.IosNodeHost
import com.bumble.appyx.navigation.integration.MainIntegrationPoint
import com.bumble.puzzyx.node.app.PuzzyxAppNode
import com.bumble.puzzyx.ui.PuzzyxTheme
import com.bumble.puzzyx.ui.appyx_dark
import kotlinx.coroutines.flow.flowOf

private val integrationPoint = MainIntegrationPoint()

@Suppress("FunctionNaming")
fun MainViewController() = ComposeUIViewController {

    PuzzyxTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = appyx_dark
        ) {
            IosNodeHost(
                modifier = Modifier,
                onBackPressedEvents = flowOf(),
                integrationPoint = remember { integrationPoint }
            ) { buildContext ->
                PuzzyxAppNode(
                    buildContext = buildContext,
                )
            }
        }
    }
}.also { uiViewController ->
    integrationPoint.setViewController(uiViewController)
}
