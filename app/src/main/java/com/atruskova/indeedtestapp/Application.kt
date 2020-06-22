package com.atruskova.indeedtestapp

import android.app.Application
import android.content.Context
import com.atruskova.indeedtestapp.helper.extensions.CommonExtensions.initDIModules

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        initDIModules()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext : Context
    }
}