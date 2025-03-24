package com.hrv.nimber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.hrv.nimber.extensions.utils.requestRequiredPermissions
import com.hrv.nimber.navigation.AppNavigation
import com.hrv.nimber.presentation.ui.components.showToast
import com.hrv.nimber.presentation.viewmodel.ConfigurationViewModel
import com.hrv.nimber.presentation.viewmodel.ReceiptViewModel
import com.hrv.nimber.presentation.viewmodel.TopBarConfig
import com.hrv.nimber.ui.theme.NimberProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object{
        lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach {}
        }
        requestRequiredPermissions { permissionsToRequest ->
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }

        setContent {
            NimberProjectTheme {
                val navController = rememberNavController()
                val configurationViewModel: ConfigurationViewModel = hiltViewModel()
                val receiptViewModel: ReceiptViewModel = hiltViewModel()

                val topBarConfig by configurationViewModel.topBarConfig.collectAsState()
                val detailReceipt by receiptViewModel.detailReceipt.collectAsState()


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                if (topBarConfig.contains(TopBarConfig.DisplayBackButton)) {
                                    IconButton(onClick = {
                                        configurationViewModel.hideBackButton()
                                        navController.popBackStack()
                                    }) {
                                        Icon(
                                            tint = Color.White,
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors()
                                .copy(containerColor = MaterialTheme.colorScheme.primary),
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        text = stringResource(R.string.app_name)
                                    )
                                }
                            },
                            actions = {
                                if (topBarConfig.contains(TopBarConfig.DisplayRightButton)) {
                                    Row(
                                        modifier = Modifier.padding(4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)

                                    ) {
                                        IconButton(
                                            modifier = Modifier.size(30.dp),
                                            onClick = {
                                                navController.popBackStack()
                                                detailReceipt?.let {
                                                    receiptViewModel.deleteReceipt(it.id)
                                                }
                                                showToast(
                                                    this@MainActivity,
                                                    getString(R.string.receipt_deleted_label)
                                                )
                                            }) {
                                            Icon(
                                                tint = Color.White,
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "description"
                                            )
                                        }

                                    }
                                }
                            }
                        )
                    },

                    ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(navController, configurationViewModel, receiptViewModel, permissionLauncher)
                    }
                }
            }
        }
    }

}
