package com.hrv.nimber.presentation.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.hrv.nimber.R

@Composable
fun MainImageWithLoader(
    url: Uri,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    errorRes: Int = R.drawable.ic_launcher_background
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        error = painterResource(id = errorRes)
    )

    Box(modifier = modifier) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
    }
}