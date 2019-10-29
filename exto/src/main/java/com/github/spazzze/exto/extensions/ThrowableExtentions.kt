package com.github.spazzze.exto.extensions

import android.annotation.SuppressLint
import android.util.Log
import com.github.spazzze.exto.R
import com.github.spazzze.exto.errors.*
import com.github.spazzze.exto.network.RetrofitException
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @author Space
 * @date 29.01.2017
 */

fun Throwable.asRetrofitException(retrofit: Retrofit): RetrofitException = when {
    this is SocketTimeoutException || this is TimeoutException || this is UnknownHostException -> RetrofitException.networkError(this, retrofit)
    this is HttpException && response() != null -> RetrofitException.httpError(this, response()!!, retrofit)
    this is IOException -> RetrofitException.networkError(this, retrofit)
    else -> RetrofitException.unexpectedError(this, retrofit)
}

fun Throwable.networkErrorMsgId(): Int = when (this) {
    is NoNetworkException -> R.string.error_no_internet
    is SocketTimeoutException -> R.string.error_no_internet
    else -> R.string.error_unknown_server_error
}

@SuppressLint("LogNotTimber")
fun Throwable.reportToDeveloper(msg: String) = when {
    this is InformativeException ||
            this is NoNetworkException ||
            this is SocketTimeoutException -> Log.d("DEV", "$msg ${this.javaClass.simpleName}").run { Unit }
    this is NotFoundException ||
            this is WrongCredentialsException ||
            this is NotAuthenticatedException -> Log.e("DEV", "$msg ${this.javaClass.simpleName}").run { Unit }
    else -> Timber.e(this, "$msg $this")
}