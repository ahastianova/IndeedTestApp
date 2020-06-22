package com.atruskova.indeedtestapp.helper.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ProcessLifecycleOwner
import com.atruskova.indeedtestapp.di.DIManager
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.isPermissionActivity
import com.atruskova.indeedtestapp.ui._base.PermissionActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object CommonExtensions {

    fun Activity.isPermissionActivity(job: (PermissionActivity) -> Unit) {
        this.takeIf { it is PermissionActivity }?.apply {
            this as PermissionActivity
            job.invoke(this)
        }
    }

    fun FragmentActivity.asAppCompatActivity() : AppCompatActivity? {
        this.takeIf { it is PermissionActivity }?.apply {
            return this as AppCompatActivity
        }
        return null
    }

    fun Activity.showMessage(@StringRes message: Int) {
        // android.R.id.content is a system default root view id
        Snackbar.make(window.decorView.findViewById(android.R.id.content), getString(message), Snackbar.LENGTH_LONG)
            .show()
    }

    fun Application.initDIModules() {
        startKoin {
            androidContext(this@initDIModules)
            modules(DIManager.getModules())
        }
    }
}