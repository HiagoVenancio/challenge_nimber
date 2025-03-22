package com.hrv.nimber.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hrv.nimber.R
import com.hrv.nimber.presentation.ui.components.MainImageWithLoader
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@Composable
fun CreateReceiptScreen(
    navController: NavHostController,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    var date by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    //var currency by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    var photoUriString by remember { mutableStateOf<String?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && photoUri != null) {
            photoUriString = photoUri.toString()
        } else {
            photoUri = null
            photoUriString = null
        }
    }

    val isReceiptValid =
        date.isNotBlank() && amount.isNotBlank() &&  photoUriString.isNullOrBlank()
            .not()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(
            onClick = {
                val uri = viewModel.createImageFileUri(context)
                photoUri = uri
                uri?.let {
                    takePictureLauncher.launch(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.capture_photo_label))
        }

        photoUriString?.let {
            MainImageWithLoader(
                it.toUri(),
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
            )
        }

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text(stringResource(R.string.date_yyyy_mm_dd_format_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(R.string.amount_label)) },
            modifier = Modifier.fillMaxWidth()
        )
     /*   OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text(stringResource(R.string.currency_label)) },
            modifier = Modifier.fillMaxWidth()
        )*/

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = isReceiptValid,
            onClick = {
                viewModel.addReceipt(
                    date = date,
                    amount = amount.toFloatOrNull() ?: 0.0f,
                    currency = "",
                    photoPath = photoUri?.toString() ?: ""
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_receipt_label))
        }
    }

}
