package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
import com.github.spazzze.exto.databinding.abs.DefaultRecyclerPageBindingHelperImpl
import com.github.spazzze.exto.databinding.abs.RecyclerBindingAdapter
import com.github.spazzze.exto.databinding.abs.RecyclerPageBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.*


@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T : IRecyclerItemViewModel> setItems(recyclerView: RecyclerView, items: ObservableList<T>?) {
    (recyclerView.adapter as? IRecyclerBindingAdapter<T>)?.apply { setItems(items ?: return) }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelper")
fun <I : IRecyclerItemViewModel> setupRecyclerView(recyclerView: RecyclerView,
                                                   bindingHelper: IRecyclerBindingHelper<I>) {
    if (recyclerView.adapter == null) recyclerView.adapter = RecyclerBindingAdapter(bindingHelper)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("paginationItems")
fun <T : IRecyclerItemViewModel> setPaginationItems(recyclerView: RecyclerView, items: ObservableList<T>?) {
    (recyclerView.adapter as? IRecyclerPageBindingAdapter<T>)?.apply { setItems(items ?: return) }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelperWithPagination")
fun <I : IRecyclerItemViewModel> setupPagedRecyclerView(recyclerView: RecyclerView,
                                                        bindingHelper: IRecyclerBindingHelper<I>) =
        setupPagedRecyclerViewWithCustomProgress(recyclerView, bindingHelper, DefaultRecyclerPageBindingHelperImpl())

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelperWithPagination", "progressBindingHelper")
fun <I : IRecyclerItemViewModel, P : IRecyclerItemViewModel> setupPagedRecyclerViewWithCustomProgress(recyclerView: RecyclerView,
                                                                                                      bindingHelper: IRecyclerBindingHelper<I>,
                                                                                                      progressBindingHelper: IRecyclerPageBindingHelper<P>) {
    if (recyclerView.adapter != null) return
    recyclerView.adapter = RecyclerPageBindingAdapter(bindingHelper, progressBindingHelper)
    val layoutManager = recyclerView.layoutManager ?: return
    if (layoutManager is GridLayoutManager) layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int = when (recyclerView.adapter?.getItemViewType(position)) {
            RecyclerPageBindingAdapter.ITEM_TYPE_PROGRESS -> layoutManager.spanCount
            else -> 1
        }
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setPageLoadingProgressVisibility")
fun <I : IRecyclerItemViewModel> setPageLoadingProgressVisibility(recyclerView: RecyclerView,
                                                                  isProgressForLoadingPageVisible: Boolean) {
    (recyclerView.adapter as? IRecyclerPageBindingAdapter<I>)?.apply { if (isProgressForLoadingPageVisible) addProgressItem() else removeProgressItem() }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("paginationItems", "layoutAnimation")
fun <T : IRecyclerItemViewModel> setPaginationItemsWithAnimation(recyclerView: RecyclerView, items: ObservableList<T>?, layoutAnimationResourceId: Int?) {
    val adapter = recyclerView.adapter as? IRecyclerPageBindingAdapter<T> ?: return
    val controller = AnimationUtils.loadLayoutAnimation(recyclerView.context, layoutAnimationResourceId ?: return)
    recyclerView.layoutAnimation = controller
    if (adapter.setItems(items ?: return)) recyclerView.scheduleLayoutAnimation()
}