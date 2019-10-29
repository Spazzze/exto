package com.github.spazzze.exto.databinding.abs

import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Space
 * @date 18.08.2019
 */

abstract class RecyclerViewEndlessOnScrollListener(private val isPageLoading: ObservableBoolean) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = -1

    private var isEndOfFeedReached = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0) return
        val layoutManager = recyclerView.layoutManager ?: return
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = when (layoutManager) {
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            else -> (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        if (visibleThreshold == -1) visibleThreshold = when (layoutManager) {
            is GridLayoutManager -> layoutManager.spanCount * 2
            else -> 2
        }
        if (!isEndOfFeedReached && !isPageLoading.get() && totalItemCount > visibleThreshold && totalItemCount - lastVisibleItem <= visibleThreshold) loadMoreItems()
    }

    abstract fun loadMoreItems()

    fun setEndOfFeedReached(isReached: Boolean) = run { isEndOfFeedReached = isReached }
}