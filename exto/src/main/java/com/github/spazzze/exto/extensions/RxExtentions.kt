package com.github.spazzze.exto.extensions

import android.content.Context
import com.github.spazzze.exto.errors.NoNetworkException
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Space
 * @date 28.01.2017
 */

//region :::::::::::::::::::::::::::::::::: Single

fun <T> Single<T>.withConnectionStatusCheck(context: Context): Single<T> = Single.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Single.error(NoNetworkException()) }

fun <T> Single<T>.runOnIoObsOnMain(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.runOnIoObsOnIo(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <reified T> Single<T>.subscribeSilent(crossinline onNextAction: (T) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}") })
}

inline fun <reified T> Single<T>.subscribeReporting(crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ Unit }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

inline fun <reified T> Single<T>.subscribeReporting(crossinline onNextAction: (T) -> Unit,
                                                    crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

//endregion :::::::::::::::::::::::::::::::::: Single

//region :::::::::::::::::::::::::::::::::: Maybe

fun <T> Maybe<T>.withConnectionStatusCheck(context: Context): Maybe<T> = Maybe.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Maybe.error(NoNetworkException()) }

fun <T> Maybe<T>.runOnIoObsOnMain(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.runOnIoObsOnIo(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <reified T> Maybe<T>.subscribeSilent(crossinline onNextAction: (T) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}") })
}

inline fun <reified T> Maybe<T>.subscribeReporting(crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ Unit }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

inline fun <reified T> Maybe<T>.subscribeReporting(crossinline onNextAction: (T) -> Unit,
                                                   crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

//endregion :::::::::::::::::::::::::::::::::: Maybe

//region :::::::::::::::::::::::::::::::::: Observable

fun <T> Observable<T>.withConnectionStatusCheck(context: Context): Observable<T> = Observable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Observable.error(NoNetworkException()) }

fun <T> Observable<T>.runOnIoObsOnMain(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.runOnIoObsOnIo(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <reified T> Observable<T>.subscribeSilent(crossinline onNextAction: (T) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}") })
}

inline fun <reified T> Observable<T>.subscribeReporting(crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ Unit }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

inline fun <reified T> Observable<T>.subscribeReporting(crossinline onNextAction: (T) -> Unit,
                                                        crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

//endregion :::::::::::::::::::::::::::::::::: Observable

//region :::::::::::::::::::::::::::::::::: Flowable
fun <T> Flowable<T>.withConnectionStatusCheck(context: Context): Flowable<T> = Flowable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Flowable.error(NoNetworkException()) }

fun <T> Flowable<T>.runOnIoObsOnMain(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.runOnIoObsOnIo(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <reified T> Flowable<T>.subscribeSilent(crossinline onNextAction: (T) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}") })
}

inline fun <reified T> Flowable<T>.subscribeReporting(crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ Unit }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

inline fun <reified T> Flowable<T>.subscribeReporting(crossinline onNextAction: (T) -> Unit,
                                                      crossinline onErrorAction: (Throwable) -> Unit) {
    subscribe({ onNextAction(it) }, { it.reportToDeveloper("${T::class.java}"); onErrorAction(it) })
}

//endregion :::::::::::::::::::::::::::::::::: Flowable

fun Disposable.addAsSingleInstanceTo(compositeSubscription: CompositeDisposable) = apply {
    compositeSubscription.clear()
    compositeSubscription.add(this)
}