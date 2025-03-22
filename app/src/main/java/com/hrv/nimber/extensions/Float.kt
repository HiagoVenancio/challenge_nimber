package com.hrv.nimber.extensions

import java.text.NumberFormat
import java.util.Locale


fun Float.toFormatCurrency(locale: Locale = Locale.getDefault()): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this)
}