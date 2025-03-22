package com.hrv.nimber.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.hrv.nimber.presentation.ui.components.SwipableImageBanner
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDetails(
    itemId: Int,
    viewModel: ReceiptViewModel
) {

    val receiptDetails by viewModel.detailReceipt.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getReceiptById(itemId)
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        receiptDetails?.let {
            SwipableImageBanner(bannerHeight = 300.dp, imageUrls = it.photoPath.map { it.toUri() })
        }
    }
}