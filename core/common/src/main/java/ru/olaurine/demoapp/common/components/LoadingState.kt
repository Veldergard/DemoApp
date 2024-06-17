package ru.olaurine.demoapp.common.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.olaurine.demoapp.common.ui.theme.DemoAppAction

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun LoadingState(@SuppressLint("ModifierParameter") modifier: Modifier = defaultModifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = DemoAppAction
    )
}