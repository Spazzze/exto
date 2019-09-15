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
import org.reactivestreams.Subscription
import java.net.InetSocketAddress
import java.net.Socket

/**
 * @author Space
 * @date 28.01.2017
 */
//region :::::::::::::::::::::::::::::::::: Single

fun <T> Single<T>.withConnectionStatusCheck(context: Context): Single<T> = Single.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Single.error(NoNetworkException()) }

fun <T> Single<T>.withConnectionStatusCheck(host: String, port: Int): Single<T> = Single.fromCallable { Socket().apply { connect(InetSocketAddress(host, port), 5000) } }
        .flatMap { it.use {}; this }

fun <T> Single<T>.runOnIoObsOnMain(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.runOnIoObsOnIo(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Single<T>.subscribeSilently(onNextAction: (T) -> Unit = {},
                                    onCompleteAction: () -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass") })

fun <T> Single<T>.subscribeReporting(onNextAction: (T) -> Unit = {},
                                     onErrorAction: (Throwable) -> Unit): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass"); onErrorAction(it) })

//endregion :::::::::::::::::::::::::::::::::: Single

//region :::::::::::::::::::::::::::::::::: Maybe

fun <T> Maybe<T>.withConnectionStatusCheck(context: Context): Maybe<T> = Maybe.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Maybe.error(NoNetworkException()) }

fun <T> Maybe<T>.withConnectionStatusCheck(host: String, port: Int): Maybe<T> = Maybe.fromCallable { Socket().apply { connect(InetSocketAddress(host, port), 5000) } }
        .flatMap { it.use {}; this }

fun <T> Maybe<T>.runOnIoObsOnMain(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.runOnIoObsOnIo(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Maybe<T>.subscribeSilently(onNextAction: (T) -> Unit = {},
                                   onCompleteAction: () -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass") }, onCompleteAction)

fun <T> Maybe<T>.subscribeReporting(onNextAction: (T) -> Unit = {},
                                    onErrorAction: (Throwable) -> Unit,
                                    onCompleteAction: () -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass"); onErrorAction(it) }, onCompleteAction)

//endregion :::::::::::::::::::::::::::::::::: Maybe

//region :::::::::::::::::::::::::::::::::: Observable

fun <T> Observable<T>.withConnectionStatusCheck(context: Context): Observable<T> = Observable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Observable.error(NoNetworkException()) }

fun <T> Observable<T>.withConnectionStatusCheck(host: String, port: Int): Observable<T> = Observable.fromCallable { Socket().apply { connect(InetSocketAddress(host, port), 5000) } }
        .flatMap { it.use {}; this }

fun <T> Observable<T>.runOnIoObsOnMain(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.runOnIoObsOnIo(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Observable<T>.subscribeSilently(onNextAction: (T) -> Unit = {},
                                        onCompleteAction: () -> Unit = {},
                                        onSubscribeAction: (Disposable) -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass") }, onCompleteAction, { onSubscribeAction(it) })

fun <T> Observable<T>.subscribeReporting(onNextAction: (T) -> Unit = {},
                                         onCompleteAction: () -> Unit = {},
                                         onErrorAction: (Throwable) -> Unit,
                                         onSubscribeAction: (Disposable) -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass"); onErrorAction(it) }, onCompleteAction, { onSubscribeAction(it) })

//endregion :::::::::::::::::::::::::::::::::: Observable

//region :::::::::::::::::::::::::::::::::: Flowable
fun <T> Flowable<T>.withConnectionStatusCheck(context: Context): Flowable<T> = Flowable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Flowable.error(NoNetworkException()) }

fun <T> Flowable<T>.withConnectionStatusCheck(host: String, port: Int): Flowable<T> = Flowable.fromCallable { Socket().apply { connect(InetSocketAddress(host, port), 5000) } }
        .flatMap { it.use {}; this }

fun <T> Flowable<T>.runOnIoObsOnMain(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.runOnIoObsOnIo(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Flowable<T>.subscribeSilently(onNextAction: (T) -> Unit = {},
                                      onCompleteAction: () -> Unit = {},
                                      onSubscribeAction: (Subscription) -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass") }, onCompleteAction, { onSubscribeAction(it) })

fun <T> Flowable<T>.subscribeReporting(onNextAction: (T) -> Unit = {},
                                       onCompleteAction: () -> Unit = {},
                                       onErrorAction: (Throwable) -> Unit,
                                       onSubscribeAction: (Subscription) -> Unit = {}): Disposable =
        subscribe({ onNextAction(it) }, { it.reportToDeveloper("$javaClass"); onErrorAction(it) }, onCompleteAction, { onSubscribeAction(it) })

//endregion :::::::::::::::::::::::::::::::::: Flowable

fun Disposable.addAsSingleInstanceTo(compositeSubscription: CompositeDisposable) = apply {
    compositeSubscription.clear()
    compositeSubscription.add(this)
}

fun CompositeDisposable.justAdd(d: Disposable): Unit = add(d).run { Unit }