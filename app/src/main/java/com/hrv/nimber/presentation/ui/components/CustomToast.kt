package com.hrv.nimber.presentation.ui.components

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, toastMessage: String?) {
    toastMessage?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}