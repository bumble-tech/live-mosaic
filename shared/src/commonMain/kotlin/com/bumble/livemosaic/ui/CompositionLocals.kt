package com.bumble.livemosaic.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalAutoPlayFlow = compositionLocalOf<State<Boolean>> { mutableStateOf(true) }
