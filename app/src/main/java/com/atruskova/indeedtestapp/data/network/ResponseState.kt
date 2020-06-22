package com.atruskova.indeedtestapp.data.network

class ResponseState(val successful: Boolean, val errorMessage: Int?) {
    companion object {
        fun success(): ResponseState {
            return ResponseState(true, null)
        }

        fun error(errorMessage: Int): ResponseState {
            return ResponseState(false, errorMessage)
        }
    }
}