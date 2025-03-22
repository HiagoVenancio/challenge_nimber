package com.hrv.nimber.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceiptScreen(
    navController: NavHostController,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    // State variables for input fields.
    var date by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }
    var photoPath by remember { mutableStateOf("No photo captured") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Receipt") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = currency,
                    onValueChange = { currency = it },
                    label = { Text("Currency") },
                    modifier = Modifier.fillMaxWidth()
                )
                // Simulate photo capture
                Button(
                    onClick = { photoPath = "photo_captured_mock_path" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Capture Photo")
                }
                Text(text = "Photo: $photoPath")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Validate and add the receipt via the ViewModel.
                        viewModel.addReceipt(
                            date = date,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            currency = currency,
                            photoPath = photoPath
                        )
                        // Navigate back after saving.
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Receipt")
                }
            }
        }
    )
}
