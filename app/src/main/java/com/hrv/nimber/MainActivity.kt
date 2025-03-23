package com.hrv.nimber

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.hrv.nimber.navigation.AppNavigation
import com.hrv.nimber.presentation.viewmodel.ConfigurationViewModel
import com.hrv.nimber.presentation.viewmodel.TopBarConfig
import com.hrv.nimber.ui.theme.NimberProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NimberProjectTheme {
                val navController = rememberNavController()
                val configurationViewModel: ConfigurationViewModel = hiltViewModel()
                val topBarConfig by configurationViewModel.topBarConfig.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            navigationIcon = {
                                if (topBarConfig == TopBarConfig.DisplayBackButton) {
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
                            }
                        )
                    },
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(navController, configurationViewModel)
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach { entry ->
                if (checkIfPermissionIsGranted(entry.key).not()) {
                    requestRequiredPermissions()
                }
            }
        }
        requestRequiredPermissions()
    }

    private fun requestRequiredPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        permissionsToRequest.add(CAMERA)

        // On Android 13 (TIRAMISU) use READ_MEDIA_IMAGES instead of READ_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsToRequest.add(READ_MEDIA_IMAGES)
        } else {
            // For older devices
            permissionsToRequest.add(READ_EXTERNAL_STORAGE)
            permissionsToRequest.add(WRITE_EXTERNAL_STORAGE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun checkIfPermissionIsGranted(permission: String) =
        (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED)
}
