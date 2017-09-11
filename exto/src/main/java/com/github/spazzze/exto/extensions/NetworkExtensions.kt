package com.github.spazzze.exto.extensions

import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString

/**
 * @author Space
 * @date 11.09.2017
 */

fun <T> T.provideBase64Auth(login: String, password: String) = with(login + ":" + password) {
    "Basic " + encodeToString(this.toByteArray(), NO_WRAP)
}