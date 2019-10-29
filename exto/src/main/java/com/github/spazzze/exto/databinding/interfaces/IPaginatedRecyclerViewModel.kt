package com.github.spazzze.exto.databinding.interfaces

import androidx.databinding.ObservableBoolean
import com.github.spazzze.exto.databinding.abs.RecyclerViewEndlessOnScrollListener
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Space
 * @date 18.08.2019
 */

interface IPaginatedRecyclerViewModel<I : IRecyclerItemViewModel> : IRecyclerViewModel<I> {

    var currentPageOffset: Int

    val pageItemsLimitToLoadAtOnce: Int

    val scrollListener: RecyclerViewEndlessOnScrollListener

    val isPageLoading: ObservableBoolean

    val isProgressForLoadingPageVisible: ObservableBoolean

    fun loadFirstItems()

    fun loadNextPageOfItems()

    override fun loadItems() = loadFirstItems()

    fun <E : Any, T : List<E>> Observable<T>.withPageLoading(): Observable<T> = this
            .doOnSubscribe { isPageLoading.set(true) }
            .doOnComplete { if (items.size >= currentPageOffset) currentPageOffset += items.size - currentPageOffset else currentPageOffset = 0 }
            .doAfterTerminate { isPageLoading.set(false) }

    fun <T> Single<T>.withPageProgress(): Single<T> = this
            .doOnSubscribe { isProgressForLoadingPageVisible.set(true) }
            .doAfterTerminate { isProgressForLoadingPageVisible.set(false) }

    fun <T> Observable<T>.withPageProgress(): Observable<T> = this
            .doOnSubscribe { isProgressForLoadingPageVisible.set(true) }
            .doAfterTerminate { isProgressForLoadingPageVisible.set(false) }
            .doOnNext { isProgressForLoadingPageVisible.set(false) }
}