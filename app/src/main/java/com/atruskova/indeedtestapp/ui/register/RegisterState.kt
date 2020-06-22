package com.atruskova.indeedtestapp.ui.register

import androidx.annotation.StringRes

/**
 * Register result : success (user details) or error message.
 */
data class RegisterState(
    val success: Boolean ,
    val isLoading: Boolean,
    val error: Int? = null
) {
    companion object {
        fun loading() : RegisterState {
            return RegisterState(false, true, null)
        }

        fun success() : RegisterState {
            return RegisterState(true, false)
        }

        fun error(msg: Int?) : RegisterState {
            return RegisterState(false, false, msg)
        }
    }
}
