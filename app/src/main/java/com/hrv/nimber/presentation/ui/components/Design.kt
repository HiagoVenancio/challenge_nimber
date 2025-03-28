package com.hrv.nimber.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorSeparator(
    color: Color = MaterialTheme.colorScheme.primary,
    heightLine: Int = 2,
    paddingHorizontal: Int = 4
) {
    Spacer(
        modifier = Modifier
            .height(heightLine.dp)
            .fillMaxWidth()
            .background(color = color)
            .padding(horizontal = paddingHorizontal.dp)
    )
}