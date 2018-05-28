package com.github.spazzze.exto.view.viewmodels.abs

import android.databinding.ObservableBoolean
import com.github.spazzze.exto.view.interfaces.IAlerter
import com.github.spazzze.exto.view.interfaces.IProgressHandler
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject

/**
 * @author Space
 * @date 27.02.2017
 */

abstract class ViewModelWithProgress(alerter: IAlerter?) : BaseViewModel(alerter), IProgressHandler {

    val isProgressBarVisible = ObservableBoolean(false)

    override fun hideProgress() = isProgressBarVisible.set(false)

    override fun showProgress() = isProgressBarVisible.set(true)

    fun <T> Single<T>.withProgress(): Single<T> = this
            .doOnSubscribe { showProgress() }
            .doAfterTerminate { hideProgress() }

    fun <T> Observable<T>.withProgress(): Observable<T> = this
            .doOnSubscribe { showProgress() }
            .doAfterTerminate { hideProgress() }
            .doOnNext { hideProgress() }

    fun <T> PublishSubject<T>.withProgress(): Observable<T> = this
            .doOnSubscribe { showProgress() }
            .doAfterTerminate { hideProgress() }
            .doOnNext { hideProgress() }
}