package com.nikasov.cafenearby.utils

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

private const val PERMISSION_REQUEST_CODE = 123

fun Context.hasLocationPermission(): Boolean {
    return EasyPermissions.hasPermissions(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}

fun Fragment.requestLocationPermission() {
    EasyPermissions.requestPermissions(
        this,
        "Just accept this",
        PERMISSION_REQUEST_CODE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
}
