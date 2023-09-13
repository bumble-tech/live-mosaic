package com.bumble.appyx.puzzyx

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.NodeView

class PuzzyxNode(
    buildContext: BuildContext,
) : Node(
    buildContext = buildContext,
    view = object : NodeView {
        @Composable
        override fun View(modifier: Modifier) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxSize(),
            ) {
                Text(
                    text = "Hello Puzzyx!",
                    fontSize = 24.sp,
                )
            }
        }
    }
)
