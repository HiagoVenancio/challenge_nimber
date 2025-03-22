package com.hrv.nimber.presentation.ui.components

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
fun ReceiptItem(receipt: ReceiptsUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "ID: ${receipt.id}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "DATE: ${receipt.date}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "AMOUNT: ${receipt.amount.toFormatCurrency()}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    }
}