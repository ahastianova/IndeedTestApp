package com.atruskova.indeedtestapp.ui._base

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.atruskova.indeedtestapp.helper.extensions.isPermissionGranted
import com.atruskova.indeedtestapp.helper.extensions.requestPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class PermissionActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val REQUEST_COD_PERMISSION = 101
    }

    private val job = SupervisorJob()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val permissionChannel = Channel<Boolean>()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_COD_PERMISSION && permissions.isNotEmpty()) {
            for (i in permissions.indices) {
                handlePermissionStatus(permissions[i], grantResults[i])
            }
        }
    }

    private fun handlePermissionStatus(permission: String, grantResult: Int) {
        when (grantResult) {
            PackageManager.PERMISSION_GRANTED -> launch {
                permissionChannel.send(true)
            }
            PackageManager.PERMISSION_DENIED -> launch {
                permissionChannel.send(false)

            }
        }
    }

    fun checkPermission(permission: String): Boolean {
        return isPermissionGranted(permission)
    }

    fun requestPermission(permission: String) {
        launch {

            when {
                isPermissionGranted(permission) -> {
                    permissionChannel.send(true)
                }
                else -> {
                    requestPermission(permission, REQUEST_COD_PERMISSION)
                }
            }

        }
    }
}