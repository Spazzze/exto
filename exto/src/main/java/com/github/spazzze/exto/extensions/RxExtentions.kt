package com.github.spazzze.exto.extensions

import android.content.Context
import com.github.spazzze.exto.errors.NoNetworkException
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Space
 * @date 28.01.2017
 */

fun <T> Single<T>.withConnectionStatusCheck(context: Context): Single<T> = Single.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Single.error(NoNetworkException()) }

fun <T> Maybe<T>.withConnectionStatusCheck(context: Context): Maybe<T> = Maybe.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Maybe.error(NoNetworkException()) }

fun <T> Observable<T>.withConnectionStatusCheck(context: Context): Observable<T> = Observable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Observable.error(NoNetworkException()) }

fun <T> Flowable<T>.withConnectionStatusCheck(context: Context): Flowable<T> = Flowable.just(context.isNetworkAvailable())
        .flatMap { if (it) this else Flowable.error(NoNetworkException()) }

fun <T> Observable<T>.runOnIoObsOnMain(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.runOnIoObsOnIo(): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Flowable<T>.runOnIoObsOnMain(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.runOnIoObsOnIo(): Flowable<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Single<T>.runOnIoObsOnMain(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.runOnIoObsOnIo(): Single<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

fun <T> Maybe<T>.runOnIoObsOnMain(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.runOnIoObsOnIo(): Maybe<T> = this
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())

inline fun <T, reified A : Any> A.silentObserver(crossinline onNextAction: (T) -> Unit) = object : Observer<T> {

    override fun onSubscribe(d: Disposable) = Maybe.just(1)
            .subscribe(reportingMaybeObserver { })

    override fun onNext(t: T) = onNextAction(t)

    override fun onComplete() = Unit

    override fun onError(e: Throwable) = e.reportToDeveloper("$javaClass")
}

inline fun <T, reified A : Any> A.silentSingleObserver(crossinline onSuccessAction: (T) -> Unit) = object : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) = Unit

    override fun onSuccess(t: T) = onSuccessAction(t)

    override fun onError(e: Throwable) = e.reportToDeveloper("$javaClass")
}

inline fun <T, reified A : Any> A.silentMaybeObserver(crossinline onSuccessAction: (T) -> Unit) = object : MaybeObserver<T> {

    override fun onComplete() = Unit

    override fun onSubscribe(d: Disposable) = Unit

    override fun onSuccess(t: T) = onSuccessAction(t)

    override fun onError(e: Throwable) = e.reportToDeveloper("$javaClass")
}

inline fun <T, reified A : Any> A.silentObserver(crossinline onNextAction: (T) -> Unit,
                                                 crossinline onCompleteAction: () -> Unit) = object : Observer<T> {

    override fun onNext(t: T) = onNextAction(t)

    override fun onCompleted() = onCompleteAction()

    override fun onError(e: Throwable) = e.reportToDeveloper("$javaClass")
}

inline fun <T, reified A : Any> A.reportingObserver(crossinline onErrorAction: (Throwable) -> Unit) = object : Observer<T> {

    override fun onSubscribe(d: Disposable) = Unit

    override fun onNext(t: T) = Unit

    override fun onComplete() = Unit

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

inline fun <T, reified A : Any> A.reportingObserver(crossinline onNextAction: (T) -> Unit,
                                                    crossinline onErrorAction: (Throwable) -> Unit) = object : Observer<T> {

    override fun onSubscribe(d: Disposable) = Unit

    override fun onNext(t: T) = onNextAction(t)

    override fun onComplete() = Unit

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

inline fun <T, reified A : Any> A.reportingMaybeObserver(crossinline onErrorAction: (Throwable) -> Unit) = object : MaybeObserver<T> {

    override fun onComplete() = Unit

    override fun onSubscribe(d: Disposable) = Unit

    override fun onSuccess(t: T) = Unit

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

inline fun <T, reified A : Any> A.reportingMaybeObserver(crossinline onSuccessAction: (T) -> Unit,
                                                         crossinline onErrorAction: (Throwable) -> Unit) = object : MaybeObserver<T> {

    override fun onComplete() = Unit

    override fun onSubscribe(d: Disposable) = Unit

    override fun onSuccess(t: T) = onSuccessAction(t)

    override fun onError(e: Throwable) {
        e.reportToDeveloper("$javaClass")
        onErrorAction(e)
    }
}

fun Disposable.addAsSingleInstanceTo(compositeSubscription: CompositeDisposable) = apply {
    compositeSubscription.clear()
    compositeSubscription.add(this)
}