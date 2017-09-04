package com.github.spazzze.exto.extensions

import android.util.Log
import com.github.spazzze.exto.R
import com.github.spazzze.exto.errors.NoNetworkException
import com.github.spazzze.exto.errors.NotAuthenticatedException
import com.github.spazzze.exto.errors.NotFoundException
import com.github.spazzze.exto.errors.WrongCredentialsError
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
        this is NotFoundException -> Log.e("DEV", "$msg NotFoundException")
        this is WrongCredentialsError -> Log.e("DEV", "$msg WrongCredentialsError")
        this is NotAuthenticatedException -> Log.e("DEV", "$msg NotAuthenticatedException")
        this is NoNetworkException -> Log.e("DEV", "$msg NoNetworkException")
        this is SocketTimeoutException -> Log.e("DEV", "$msg SocketTimeoutException")
        else -> Timber.e("DEV", "$msg $this")
    }
}