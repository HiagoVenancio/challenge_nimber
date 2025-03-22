package com.hrv.nimber.data.mapper

import com.hrv.nimber.data.local.ReceiptEntity
import com.hrv.nimber.presentation.viewmodel.ReceiptsUiModel

// Extension function to map a ReceiptEntity to a ReceiptsUiModel
fun ReceiptEntity.toUiModel(): ReceiptsUiModel = ReceiptsUiModel(
    id = this.id,
    date = this.date,
    amount = this.amount,
    currency = this.currency,
    photoPath = this.photoPath
)
