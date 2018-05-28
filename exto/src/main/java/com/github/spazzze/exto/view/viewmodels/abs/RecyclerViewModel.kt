package com.github.spazzze.exto.view.viewmodels.abs

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.StringRes
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel
import com.github.spazzze.exto.databinding.interfaces.IRecyclerViewModel
import com.github.spazzze.exto.view.interfaces.IAlerter
import rx.Observable
import rx.Single
import rx.subscriptions.CompositeSubscription

/**
 * @author Space
 * @date 06.02.2017
 */

abstract class RecyclerViewModel<I : IRecyclerItemViewModel>(@StringRes defaultNoContentStringId: Int,
                                                             val subscription: CompositeSubscription,
                                                             alerter: IAlerter?) :
        ViewModelWithProgress(alerter), IRecyclerViewModel<I> {

    val defaultNoContentText = ctx.getString(defaultNoContentStringId)!!

    override val items = ObservableArrayList<I>()
    val noContentTextVisibility = ObservableBoolean(false)
    val isRefreshing = ObservableBoolean(false)
    val noContentText = ObservableField(ctx.getString(defaultNoContentStringId))

    abstract fun loadItems()

    abstract fun refreshItems()

    abstract fun handleNetworkException(t: Throwable, isAlertNeeded: Boolean)/*= t.networkErrorMsgId().let {
   if (isAlertNeeded) alerter?.showAlert(ctx.getString(it))
   if (items.isEmpty()) noContentText.set(ctx.getString(it))
}*/

    open fun updateUI() = noContentTextVisibility.set(items.isEmpty())

    fun <T> Observable<T>.withUiUpdate(): Observable<T> = this
            .doOnSubscribe { noContentTextVisibility.set(false) }
            .doOnCompleted { noContentText.set(defaultNoContentText) }
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