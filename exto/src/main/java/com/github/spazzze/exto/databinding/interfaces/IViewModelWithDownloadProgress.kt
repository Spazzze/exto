package com.github.spazzze.exto.databinding.interfaces

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import com.github.spazzze.exto.network.progress.SimpleProgressListenerImpl
import rx.Observable
import rx.Single
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

/**
 * @author Space
 * @date 04.06.2018
 */

interface IViewModelWithDownloadProgress : IViewModelWithProgress {

    var downloadSubscription: Subscription?

    val compositeSubscription: CompositeSubscription

    val isDownloadProgressVisible: ObservableBoolean

    val downloadProgress: ObservableInt

    val progressListener: SimpleProgressListenerImpl

    fun startSingleDownload(s: Subscription) {
        downloadSubscription = s
        compositeSubscription.add(downloadSubscription)
    }

    fun abortSingleDownload() = downloadSubscription?.apply {
        downloadProgress.set(0)
        compositeSubscription.remove(this)
        unsubscribe()
    }

    fun hideDownloadProgress() = isDownloadProgressVisible.set(false).apply { downloadProgress.set(0) }

    fun showDownloadProgress() = isDownloadProgressVisible.set(true)

    fun <T> Single<T>.withDownloadProgress(): Single<T> = this
            .doOnSubscribe { showDownloadProgress() }
            .doAfterTerminate { hideDownloadProgress() }

    fun <T> Observable<T>.withDownloadProgress(): Observable<T> = this
            .doOnSubscribe { showDownloadProgress() }
            .doAfterTerminate { hideDownloadProgress() }
            .doOnNext { hideDownloadProgress() }

    fun <T> PublishSubject<T>.withDownloadProgress(): Observable<T> = this
            .doOnSubscribe { showDownloadProgress() }
            .doAfterTerminate { hideProgress() }
            .doOnNext { hideDownloadProgress() }
}