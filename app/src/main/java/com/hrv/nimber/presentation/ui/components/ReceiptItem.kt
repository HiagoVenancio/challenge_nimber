package com.hrv.nimber.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.hrv.nimber.extensions.toFormatCurrency
import com.hrv.nimber.presentation.viewmodel.ReceiptsUiModel

@Composable
fun ReceiptItem(receipt: ReceiptsUiModel, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClickItem.invoke(receipt.id)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            MainImageWithLoader(
                receipt.photoPath.first().toUri(),
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)) {
                TextBodyLarge(title = "Id: ${receipt.id}")
                TextBodyLarge(title = "Date: ${receipt.date}")
                TextBodyLarge(title = "Amount: ${receipt.amount.toFormatCurrency()}")
            }
        }

    }
}