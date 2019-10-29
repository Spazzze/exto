package com.github.spazzze.exto.databinding.abs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableList
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerPageBindingHelper

/**
 * @author Space
 * @date 20.08.2019
 */

@Suppress("UNCHECKED_CAST")
open class RecyclerPageBindingAdapter<I : IRecyclerItemViewModel, out P : IRecyclerItemViewModel>(
        bindingHelper: IRecyclerBindingHelper<I>,
        private val progressBindingHelper: IRecyclerPageBindingHelper<P> = DefaultRecyclerPageBindingHelperImpl() as IRecyclerPageBindingHelper<P>,
        items: ObservableList<I> = ObservableArrayList()) : RecyclerBindingAdapter<I>(bindingHelper, items), IRecyclerPageBindingAdapter<I> {

    override val progressItemIndex = ObservableInt(-1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            if (viewType == ITEM_TYPE_DATA) bindingHelper.layoutRes else progressBindingHelper.layoutRes, parent, false))

    override fun onBindViewHolder(holder: BindingHolder, position: Int) = holder.binding.run {
        when {
            progressItemIndex.get() == -1 || position < progressItemIndex.get() -> setVariable(bindingHelper.variableId, items[position])
            progressItemIndex.get() == position -> setVariable(progressBindingHelper.variableId, progressBindingHelper.progressViewModel)
            else -> setVariable(bindingHelper.variableId, items[position - 1])
        }
        executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int = if (progressItemIndex.get() == position) ITEM_TYPE_PROGRESS else ITEM_TYPE_DATA

    override fun addProgressItem() = if (progressItemIndex.get() == -1) synchronized(items) {
        progressItemIndex.set(items.lastIndex + 1)
        notifyItemInserted(progressItemIndex.get())
    } else Unit

    override fun removeProgressItem(): Boolean = if (progressItemIndex.get() == -1) false else synchronized(items) {
        notifyItemRemoved(progressItemIndex.get())
        notifyItemRangeChanged(progressItemIndex.get(), itemCount)
        progressItemIndex.set(-1)
        return true
    }

    override fun getItemCount() = if (progressItemIndex.get() != -1) items.size + 1 else items.size

    override fun setItems(newItems: List<I>): Boolean = super.setItems(newItems).also { if (it) removeProgressItem() }

    companion object {
        const val ITEM_TYPE_PROGRESS = 1
        const val ITEM_TYPE_DATA = 2
    }
}