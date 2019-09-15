package com.github.spazzze.exto.network

import android.support.annotation.Keep
import com.github.spazzze.exto.network.progress.ProgressResponseBody
import com.github.spazzze.exto.network.progress.RequestProgressListener
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * @author elena
 * @date 10/03/17
 */

@Keep
object DownloadUtils {

    fun download(client: OkHttpClient, url: String): Observable<Response> = Observable.just(url)
            .map { client.newCall(Request.Builder().url(it).build()).execute() }
            .subscribeOn(Schedulers.io())

    fun downloadWithProgress(url: String, progressListener: RequestProgressListener): Observable<Response> = Observable.just(url)
            .map { generateProgressClient(progressListener).newCall(Request.Builder().url(it).build()).execute() }
            .subscribeOn(Schedulers.io())

    private fun generateProgressClient(progressListener: RequestProgressListener): OkHttpClient = OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                originalResponse.newBuilder().body(ProgressResponseBody(originalResponse.body()!!, progressListener)).build()
            }.build()
}