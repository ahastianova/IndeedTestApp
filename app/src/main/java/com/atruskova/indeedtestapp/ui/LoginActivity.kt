package com.atruskova.indeedtestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.ui._base.PermissionActivity
import com.atruskova.indeedtestapp.ui.login.LoginFragment

class LoginActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            super.onBackPressed()
    }


}