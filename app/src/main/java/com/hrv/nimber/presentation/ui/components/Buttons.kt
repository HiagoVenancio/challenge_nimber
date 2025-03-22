package com.hrv.nimber.presentation.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ButtonWithTextAndAction(
    textResource: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        modifier = modifier
    ) {
        Text(stringResource(textResource))
    }
}