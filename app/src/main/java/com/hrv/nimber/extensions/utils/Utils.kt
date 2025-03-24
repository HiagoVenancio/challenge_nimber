package com.hrv.nimber.extensions.utils

fun isValidDate(date: String): Boolean {
    val dateRegex = Regex("""^(19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$""")
    return dateRegex.matches(date)
}

fun getAmountFormated(amount: String): Float? {
    val formattedAmount = amount.replace(",", ".")
    return formattedAmount.toFloatOrNull()
}