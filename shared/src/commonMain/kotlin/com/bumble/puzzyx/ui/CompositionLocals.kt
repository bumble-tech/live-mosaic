package com.bumble.puzzyx.ui

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val LocalAutoPlayFlow = compositionLocalOf<StateFlow<Boolean>> { MutableStateFlow(true) }
