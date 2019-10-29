package com.github.spazzze.exto.databinding.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.github.spazzze.exto.network.progress.SimpleProgressListenerImpl
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * @author Space
 * @date 04.06.2018
 */

interface IViewModelWithDownloadProgress : IViewModelWithProgress {

    var downloadSubscription: Disposable?

    val compositeSubscription: CompositeDisposable

    val isDownloadProgressVisible: ObservableBoolean

    val downloadProgress: ObservableInt

    val progressListener: SimpleProgressListenerImpl

    fun startSingleDownload(s: Disposable) {
        downloadSubscription = s
        downloadSubscription?.run { compositeSubscription.add(this) }
    }

    fun abortSingleDownload() = downloadSubscription?.apply {
        downloadProgress.set(0)
        compositeSubscription.remove(this)
        dispose()
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