package com.hrv.nimber.extensions.utils

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkIfPermissionIsGranted(context: Context, permission: String) =
    (ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED)

fun requestRequiredPermissions(permissions: (MutableList<String>) -> Unit): MutableList<String> {
    val permissionsToRequest = mutableListOf<String>()

    permissionsToRequest.add(CAMERA)
    permissionsToRequest.add(READ_EXTERNAL_STORAGE)
    permissionsToRequest.add(WRITE_EXTERNAL_STORAGE)

    if (permissionsToRequest.isNotEmpty()) {
        permissions.invoke(permissionsToRequest)
    }
    return permissionsToRequest
}
