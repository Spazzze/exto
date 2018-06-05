package com.github.spazzze.exto.network

import com.github.spazzze.exto.extensions.asRetrofitException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Single
import java.lang.reflect.Type

/**
 * @author Space
 * @date 13.09.2017
 */

@Suppress("UNCHECKED_CAST")
class RxErrorHandlingCallAdapterFactory(private val original: RxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create()) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? =
            original.get(returnType, annotations, retrofit)?.let { RxCallAdapterWrapper(retrofit, it) }

    private class RxCallAdapterWrapper<R>(private val retrofit: Retrofit, private val wrapped: CallAdapter<R, *>) : CallAdapter<R, Single<R>> {

        override fun adapt(call: Call<R>): Single<R> = (wrapped.adapt(call) as Single<R>)
                .onErrorResumeNext { Single.error(it.asRetrofitException(retrofit)) }

        override fun responseType(): Type = wrapped.responseType()
    }
}