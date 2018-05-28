package com.github.spazzze.exto.databinding

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import com.github.spazzze.exto.databinding.abs.RecyclerBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel

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