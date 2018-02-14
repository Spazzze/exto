package com.github.spazzze.exto.extensions

import android.content.Context
import com.github.spazzze.exto.errors.NoNetworkException
import rx.Observable
import rx.Observer
import rx.Single
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * @author Space
 * @date 28.01.2017
 */

fun <T> Single<T>.withConnectionStatusCheck(context: Context): Single<T> = Single.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Single.error(NoNetworkException()) }

fun <T> Observable<T>.withConnectionStatusCheck(context: Context): Observable<T> = Observable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Observable.error(NoNetworkException()) }

fun <T> Observable<T>.runOnIoObsOnMain(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.runOnIoObsOnIo(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Single<T>.runOnIoObsOnMain(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.runOnIoObsOnIo(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <T, reified A : Any> A.silentObserver(crossinline onNextAction: (T) -> Unit) = object : Observer<T> {

    override fun onNext(t: T) = onNextAction(t)

    override fun onCompleted() = Unit

    override fun onError(e: Throwable) = e.reportToDeveloper("$javaClass")
}

inline fun <T, reified A : Any> A.reportingObserver(crossinline onErrorAction: (Throwable) -> Unit) = object : Observer<T> {

    override fun onNext(t: T) = Unit

    override fun onCompleted() = Unit

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

inline fun <T, reified A : Any> A.reportingObserver(crossinline onNextAction: (T) -> Unit,
                                                    crossinline onErrorAction: (Throwable) -> Unit) = object : Observer<T> {
    override fun onNext(t: T) = onNextAction(t)

    override fun onCompleted() = Unit

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

fun Subscription.addAsSingleInstanceTo(compositeSubscription: CompositeSubscription) = apply {
    compositeSubscription.clear()
    compositeSubscription.add(this)
}