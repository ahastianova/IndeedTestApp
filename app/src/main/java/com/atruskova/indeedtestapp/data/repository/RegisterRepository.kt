package com.atruskova.indeedtestapp.data.repository

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.core.net.toUri
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.data.models.SampleAppUser
import com.atruskova.indeedtestapp.data.network.ResponseState
import com.atruskova.indeedtestapp.helper.utils.CryptographyManager
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.lang.Exception

private const val TAG = "RegisterRepository"

class RegisterRepository(
    private val context: Context,
    private val cryptographyManager: CryptographyManager
) {
    companion object {
        const val URL_HOST = "secure"
        const val URL_LOGIN = "login"
        const val URL_PASSWORD = "password"
    }

     suspend fun decodeCredentialsFromBase64(from: String?): ResponseState {
            val decodeString = String(Base64.decode(from, DEFAULT_BUFFER_SIZE))
            val url = decodeString.toUri()
            url?.let {
                if (it.host == URL_HOST) {

                    val login = it.getQueryParameter(URL_LOGIN)
                    val password = it.getQueryParameter(URL_PASSWORD)

                    if (!login.isNullOrEmpty() && !password.isNullOrEmpty()) {
                        SampleAppUser.apply {
                            this.username = login
                        }
                        return ResponseState.success()
                    }
                }
                ResponseState.error(R.string.register_decode_host_error)
            }
            return ResponseState.error(R.string.register_undefined_decode_error)
    }
}