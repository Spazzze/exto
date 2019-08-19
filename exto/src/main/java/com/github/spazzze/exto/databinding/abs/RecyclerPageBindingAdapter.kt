package com.github.spazzze.exto.databinding.abs

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableInt
import android.databinding.ObservableList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.spazzze.exto.BR
import com.github.spazzze.exto.R
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageItemViewModel
import com.github.spazzze.exto.view.viewmodels.ProgressItemViewModel

/**
 * @author Space
 * @date 18.08.2019
 */


class RecyclerPageBindingAdapter<I : IRecyclerPageItemViewModel>(bindingHelper: IRecyclerBindingHelper<I>,
                                                                 items: ObservableList<I> = ObservableArrayList()) :
        RecyclerBindingAdapter<I>(bindingHelper, items), IRecyclerPageBindingAdapter<I> {

    private val progressItemIndex = ObservableInt(-1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            if (viewType == ITEM_TYPE_DATA) bindingHelper.layoutRes else R.layout.item_page_progress, parent, false))

    override fun onBindViewHolder(holder: BindingHolder, position: Int) = items[position].let {
        holder.binding.run {
            if (it.isProgressForLoadingPageVisible.get()) setVariable(BR.progressViewModel, ProgressItemViewModel()) else setVariable(bindingHelper.variableId, it)
            executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int = if (items[position].isProgressForLoadingPageVisible.get()) ITEM_TYPE_PROGRESS else ITEM_TYPE_DATA

    override fun addProgressItem(item: I) = if (progressItemIndex.get() == -1) synchronized(items) {
        item.isProgressForLoadingPageVisible.set(true)
        items.add(item)
        progressItemIndex.set(items.lastIndex)
        notifyItemInserted(progressItemIndex.get())
    } else Unit

    override fun removeProgressItem(): Boolean = if (progressItemIndex.get() == -1) false else synchronized(items) {
        if (items.isEmpty()) return false
        items.removeAt(progressItemIndex.get())
        notifyItemRemoved(progressItemIndex.get())
        notifyItemRangeChanged(progressItemIndex.get(), itemCount)
        progressItemIndex.set(-1)
        return true
    }

    override fun setItems(newItems: List<I>): Boolean = super.setItems(newItems).also { if (it) removeProgressItem() }

    companion object {
        const val ITEM_TYPE_PROGRESS = 1
        const val ITEM_TYPE_DATA = 2
    }
}