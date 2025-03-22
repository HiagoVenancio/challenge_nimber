package com.hrv.nimber.data.mapper

import com.hrv.nimber.data.local.ReceiptEntity
import com.hrv.nimber.presentation.viewmodel.ReceiptsUiModel

fun ReceiptEntity.toUiModel(): ReceiptsUiModel = ReceiptsUiModel(
    id = this.id,
    date = this.date,
    amount = this.amount,
    photoPath = this.photoPaths
)
