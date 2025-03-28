package com.hrv.nimber.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.hrv.nimber.extensions.toFormatCurrency
import com.hrv.nimber.presentation.ui.components.ColorSeparator
import com.hrv.nimber.presentation.ui.components.SwipableImageBanner
import com.hrv.nimber.presentation.ui.components.TextTitleLarge
import com.hrv.nimber.presentation.viewmodel.ConfigurationViewModel
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@Composable
fun ReceiptDetails(
    navController: NavHostController,
    itemId: Int,
    viewModel: ReceiptViewModel,
    configurationViewModel: ConfigurationViewModel
) {
    val receiptDetails by viewModel.detailReceipt.collectAsState()

    LaunchedEffect(Unit) {
        configurationViewModel.displayBackButton()
        configurationViewModel.displayRightButton()
        viewModel.getReceiptById(itemId)
    }

    BackHandler(enabled = true) {
        configurationViewModel.hideBackButton()
        navController.popBackStack()
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        receiptDetails?.let {
            SwipableImageBanner(bannerHeight = 300.dp, imageUrls = it.photoPath.map { it.toUri() })
        }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp)
        ) {
            ColorSeparator()
            TextTitleLarge(title = "Id: ${receiptDetails?.id}")
            ColorSeparator()
            TextTitleLarge(title = "Date: ${receiptDetails?.date}")
            ColorSeparator()
            TextTitleLarge(title = "Amount: ${receiptDetails?.amount?.toFormatCurrency()}")
            ColorSeparator()
        }
    }
}

