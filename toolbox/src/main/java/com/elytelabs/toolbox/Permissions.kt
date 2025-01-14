package com.elytelabs.toolbox

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.requestPermission(permission: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this as Activity, arrayOf(permission), requestCode)
}