package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationUtils
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
fun <T : IRecyclerPageItemViewModel> setPaginationItems(recyclerView: RecyclerView, items: ObservableList<T>?) {
    (recyclerView.adapter as? IRecyclerPageBindingAdapter<T>)?.apply { setItems(items ?: return) }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelperWithPagination")
fun <I : IRecyclerPageItemViewModel> setupPagedRecyclerView(recyclerView: RecyclerView,
                                                            bindingHelper: IRecyclerBindingHelper<I>) {
    if (recyclerView.adapter == null) recyclerView.adapter = RecyclerPageBindingAdapter(bindingHelper)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("setPageLoadingProgressVisibility", "progressItem")
fun <I : IRecyclerPageItemViewModel> setPageLoadingProgressVisibility(recyclerView: RecyclerView,
                                                                      isProgressForLoadingPageVisible: Boolean,
                                                                      item: I) {
    (recyclerView.adapter as? IRecyclerPageBindingAdapter<I>)
            ?.apply { if (isProgressForLoadingPageVisible) addProgressItem(item) else removeProgressItem() }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("paginationItems", "layoutAnimation")
fun <T : IRecyclerPageItemViewModel> setPaginationItemsWithAnimation(recyclerView: RecyclerView, items: ObservableList<T>?, layoutAnimationResourceId: Int?) {
    val adapter = recyclerView.adapter as? RecyclerPageBindingAdapter<T> ?: return
    val controller = AnimationUtils.loadLayoutAnimation(recyclerView.context, layoutAnimationResourceId ?: return)
    recyclerView.layoutAnimation = controller
    if (adapter.setItems(items ?: return)) recyclerView.scheduleLayoutAnimation()
}