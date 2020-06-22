package com.atruskova.indeedtestapp.helper.utils

import android.util.Base64

object DecodeUtil {
    suspend fun decodeString(from: String): String {
        return String(Base64.decode(from, DEFAULT_BUFFER_SIZE))
    }
}