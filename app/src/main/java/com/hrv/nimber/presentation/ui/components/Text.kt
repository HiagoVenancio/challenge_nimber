package com.hrv.nimber.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextBodyLarge(
    title: String,
    color: Color = Color.Black,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            fontFamily = FontFamily.Serif,
            color = color,
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TextTitleLarge(
    title: String,
    color: Color = Color.White,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            fontFamily = FontFamily.Serif,
            color = color,
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}