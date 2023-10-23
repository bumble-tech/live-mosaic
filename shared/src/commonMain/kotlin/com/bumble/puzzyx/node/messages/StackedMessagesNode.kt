package com.bumble.puzzyx.node.messages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.interactions.permanent.PermanentAppyxComponent
import com.bumble.appyx.navigation.composable.PermanentChild
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize
import com.bumble.puzzyx.data.EntryDataSource
import com.bumble.puzzyx.model.Entry
import com.bumble.puzzyx.model.MessageId
import kotlinx.coroutines.runBlocking

class StackedMessagesNode(
    buildContext: BuildContext,
    private val entryDataSource: EntryDataSource,
    private val groupCount: Int = DEFAULT_GROUP_COUNT,
    private val groupSize: Int = DEFAULT_GROUP_SIZE,
    // Not ideal but make the trick.
    private val groupedMessages: List<List<Entry>> = runBlocking {
        entryDataSource.getFeaturedEntries(
            entriesCount = groupCount * groupSize,
            newestEntriesCount = groupCount * (groupSize - 1),
        ).windowed(groupSize, groupSize, false).toMutableList()
    },
    private val permanentAppyxComponent: PermanentAppyxComponent<InteractionTarget> = PermanentAppyxComponent(
        savedStateMap = buildContext.savedStateMap,
        initialTargets = List(groupCount) { index -> InteractionTarget.Messages(index) },
    ),
) : ParentNode<StackedMessagesNode.InteractionTarget>(
    buildContext = buildContext,
    appyxComponent = permanentAppyxComponent
) {

    sealed class InteractionTarget : Parcelable {
        @Parcelize
        data class Messages(val index: Int) : InteractionTarget()
    }

    override fun resolve(interactionTarget: InteractionTarget, buildContext: BuildContext): Node =
        when (interactionTarget) {
            is InteractionTarget.Messages -> MessagesNode(
                buildContext = buildContext,
                index = interactionTarget.index,
                messages = groupedMessages[interactionTarget.index].indices.map { MessageId(it) },
                localEntries = groupedMessages[interactionTarget.index],
            ) { if (it == groupCount - 1) finish() }
        }

    @Composable
    override fun View(modifier: Modifier) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            groupedMessages.forEachIndexed { index, _ ->
                PermanentChild(
                    permanentAppyxComponent = permanentAppyxComponent,
                    interactionTarget = InteractionTarget.Messages(
                        index = index,
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private companion object {
        const val DEFAULT_GROUP_COUNT = 3
        const val DEFAULT_GROUP_SIZE = 7
    }
}
