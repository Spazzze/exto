package com.github.spazzze.exto.databinding.interfaces

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Space
 * @date 06.04.2017
 */

interface IRecyclerViewModel<I : IRecyclerItemViewModel> : IViewModelWithProgress {

    val isRefreshing: ObservableBoolean

    val subscription: CompositeDisposable

    val items: ObservableList<I>

    val bindingHelper: IRecyclerBindingHelper<I>

    val defaultNoContentText: String

    val noContentTextVisibility: ObservableBoolean

    val noContentText: ObservableField<String>

    fun loadItems()

    fun refreshItems()

    fun handleNetworkException(t: Throwable, isAlertNeeded: Boolean)

    fun updateUI() = noContentTextVisibility.set(items.isEmpty())

    fun <T> Observable<T>.withUiUpdate(): Observable<T> = this
            .doOnSubscribe { noContentTextVisibility.set(false) }
            .doOnComplete { noContentText.set(defaultNoContentText) }
            .doOnTerminate { updateUI() }

    fun <T> Observable<T>.withRefresh(): Observable<T> = this
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }

    fun <T> Single<T>.withUiUpdate(): Single<T> = this
            .doOnSubscribe { noContentTextVisibility.set(false) }
            .doOnSuccess { noContentText.set(defaultNoContentText) }
            .doAfterTerminate { updateUI() }

    fun <T> Single<T>.withRefresh(): Single<T> = this
            .doOnSubscribe { isRefreshing.set(true) }
            .doAfterTerminate { isRefreshing.set(false) }
}