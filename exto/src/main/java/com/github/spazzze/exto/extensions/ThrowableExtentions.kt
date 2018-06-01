package com.github.spazzze.exto.extensions

import android.util.Log
import com.github.spazzze.exto.R
import com.github.spazzze.exto.errors.*
import timber.log.Timber
import java.net.SocketTimeoutException

/**
 * @author Space
 * @date 29.01.2017
 */

fun Throwable.networkErrorMsgId(): Int = when {
    this is NoNetworkException -> R.string.error_no_internet
    this is SocketTimeoutException -> R.string.error_no_internet
    else -> R.string.error_unknown_server_error
}

fun Throwable.reportToDeveloper(clazz: String) {
    val msg = "$clazz failure in subscription: "
    when {
        this is InformativeException ||
                this is NoNetworkException ||
                this is SocketTimeoutException -> Log.d("DEV", "$msg ${this.javaClass.simpleName}")
        this is NotFoundException ||
                this is WrongCredentialsException ||
                this is NotAuthenticatedException -> Log.e("DEV", "$msg ${this.javaClass.simpleName}")
        else -> Timber.e(this, "$msg $this")
    }
}