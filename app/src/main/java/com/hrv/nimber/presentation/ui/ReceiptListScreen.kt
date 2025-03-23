package com.hrv.nimber.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.hrv.nimber.navigation.Screen
import com.hrv.nimber.presentation.ui.components.ReceiptItem
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel

@Composable
fun ReceiptListScreen(
    navController: NavHostController,
    viewModel: ReceiptViewModel,
    onAddClick: () -> Unit = {}
) {
    val receiptsState = viewModel.receipts.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddClick.invoke()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Receipt"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {
            items(receiptsState.value) { receipt ->
                ReceiptItem(receipt) {
                    navController.navigate(Screen.DetailsReceiptScreen.createRoute(receipt.id.toString()))
                }
            }
        }
    }
}


