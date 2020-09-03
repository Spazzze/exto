package com.github.spazzze.exto.databinding

import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.spazzze.exto.databinding.abs.*
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
    val controller = AnimationUtils.loadLayoutAnimation(recyclerView.context, layoutAnimationResourceId
            ?: return)
    recyclerView.layoutAnimation = controller
    if (adapter.setItems(items ?: return)) recyclerView.scheduleLayoutAnimation()
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelperWithPaginationBottomSpacing")
fun <I : IRecyclerItemViewModel> setupPagedRecyclerViewBottomSpacing(recyclerView: RecyclerView,
                                                                     bindingHelper: IRecyclerBindingHelper<I>) =
        setupPagedRecyclerViewWithCustomProgressBottomSpacing(recyclerView, bindingHelper, DefaultRecyclerPageBindingHelperImpl(), DefaultRecyclerPageEmptyBindingHelperImpl())

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindingHelperWithPaginationBottomSpacing", "progressBindingHelper", "emptyBindingHelper")
fun <I : IRecyclerItemViewModel, P : IRecyclerItemViewModel, E : IRecyclerItemViewModel> setupPagedRecyclerViewWithCustomProgressBottomSpacing(recyclerView: RecyclerView,
                                                                                                                                               bindingHelper: IRecyclerBindingHelper<I>,
                                                                                                                                               progressBindingHelper: IRecyclerPageBindingHelper<P>,
                                                                                                                                               emptyBindingHelper: IRecyclerPageEmptyBindingHelper<E>) {
    if (recyclerView.adapter != null) return
    recyclerView.adapter = RecyclerPageBindingAdapterWithSpace(bindingHelper, progressBindingHelper, emptyBindingHelper)
    val layoutManager = recyclerView.layoutManager ?: return
    if (layoutManager is GridLayoutManager) layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int = when (recyclerView.adapter?.getItemViewType(position)) {
            RecyclerPageBindingAdapterWithSpace.ITEM_TYPE_PROGRESS, RecyclerPageBindingAdapterWithSpace.ITEM_TYPE_SPACE -> layoutManager.spanCount
            else -> 1
        }
    }
}