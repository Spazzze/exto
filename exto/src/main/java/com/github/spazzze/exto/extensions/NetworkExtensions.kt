package com.github.spazzze.exto.extensions

import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString

/**
 * @author Space
 * @date 11.09.2017
 */

fun provideBase64Auth(login: String, password: String) = when (login.isNotBlank() && password.isNotBlank()) {
    true -> "Basic " + encodeToString("$login:$password".toByteArray(), NO_WRAP)
    else -> null
}