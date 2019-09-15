package com.github.spazzze.exto.network

import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


/**
 * @author Space
 * @date 13.09.2017
 */


class RetrofitException private constructor(exception: Throwable,
                                            val kind: Kind = Kind.UNEXPECTED,
                                            val retrofit: Retrofit,
                                            val response: Response<*>? = null) : RuntimeException(exception) {

//    val apiResponse: RestApiError? by lazy { if (kind == Kind.HTTP) getErrorBodyAs(RestApiError::class.java) else null }

    enum class Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    private fun <T> getErrorBodyAs(type: Class<T>): T? = try {
        response?.errorBody()?.let { retrofit.responseBodyConverter<T>(type, arrayOfNulls<Annotation>(0))?.convert(it) }
    } catch (e: IOException) {
        null
    }

    companion object {
        fun httpError(exception: Throwable, response: Response<*>, retrofit: Retrofit) = RetrofitException(exception, Kind.HTTP, retrofit, response)
        fun networkError(exception: Throwable, retrofit: Retrofit) = RetrofitException(exception, Kind.NETWORK, retrofit)
        fun unexpectedError(exception: Throwable, retrofit: Retrofit) = RetrofitException(exception, retrofit = retrofit)
    }
}