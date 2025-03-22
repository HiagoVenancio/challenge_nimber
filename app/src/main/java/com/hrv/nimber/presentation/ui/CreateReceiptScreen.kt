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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hrv.nimber.R
import com.hrv.nimber.presentation.ui.components.MainImageWithLoader
import com.hrv.nimber.presentation.ui.components.showToast
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@Composable
fun CreateReceiptScreen(
    navController: NavHostController,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var date by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isValidDate by remember { mutableStateOf<Boolean?>(null) }
    var photoUriString by remember { mutableStateOf<String?>(null) }
    val photoUriList = remember { mutableStateListOf<Uri>() }
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val maxPhotos = 3

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && currentPhotoUri != null) {
            if (photoUriList.size < maxPhotos) {
                currentPhotoUri?.let {
                    photoUriList.add(it)
                }
            } else {
                showToast(context, "You can only add $maxPhotos pictures.")
            }
        } else {
            currentPhotoUri = null
            photoUriString = null
        }
    }

    val isReceiptValid =
        date.isNotBlank() && amount.isNotBlank() && photoUriList.isNotEmpty() && (isValidDate == true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(
            onClick = {
                val uri = viewModel.createImageFileUri(context)
                currentPhotoUri = uri
                uri?.let {
                    takePictureLauncher.launch(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.capture_photo_label))
        }

        photoUriList.forEach { uri ->
            MainImageWithLoader(
                url = uri,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
        }

        OutlinedTextField(
            isError = isValidDate?.not() ?: false,
            singleLine = true,
            value = date,
            onValueChange = { input ->
                date = input.take(10)
                if (input.length == 10) {
                    isValidDate = isValidDate(date)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            label = { Text(stringResource(R.string.date_yyyy_mm_dd_format_label)) },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            singleLine = true,
            value = amount,
            onValueChange = { amount = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            label = { Text(stringResource(R.string.amount_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = isReceiptValid,
            onClick = {
                viewModel.addReceipt(
                    date = date,
                    amount = amount.toFloatOrNull() ?: 0.0f,
                    photoPath = photoUriList.map { it.toString() }
                )
                showToast(context, context.getString(R.string.receipt_added_label))
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_receipt_label))
        }
    }

}

fun isValidDate(date: String): Boolean {
    val dateRegex = Regex("""^(19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$""")
    return dateRegex.matches(date)
}

