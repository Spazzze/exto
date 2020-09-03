package com.github.spazzze.exto.databinding.abs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.*
import com.github.spazzze.exto.databinding.interfaces.*

@Suppress("UNCHECKED_CAST")
open class RecyclerPageBindingAdapterWithSpace<I : IRecyclerItemViewModel, out P : IRecyclerItemViewModel, out E : IRecyclerItemViewModel>(
        bindingHelper: IRecyclerBindingHelper<I>,
        private val progressBindingHelper: IRecyclerPageBindingHelper<P> = DefaultRecyclerPageBindingHelperImpl() as IRecyclerPageBindingHelper<P>,
        private val emptyBindingHelper: IRecyclerPageEmptyBindingHelper<E> = DefaultRecyclerPageEmptyBindingHelperImpl() as IRecyclerPageEmptyBindingHelper<E>,
        items: ObservableList<I> = ObservableArrayList()) : RecyclerBindingAdapter<I>(bindingHelper, items), IRecyclerPageBindingAdapter<I> {

    override val progressItemIndex = ObservableInt(-1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            when (viewType) {
                ITEM_TYPE_SPACE -> emptyBindingHelper.layoutRes
                ITEM_TYPE_PROGRESS -> progressBindingHelper.layoutRes
                else -> bindingHelper.layoutRes
            }, parent, false))


    override fun onBindViewHolder(holder: BindingHolder, position: Int) = holder.binding.run {
        when {
            position == itemCount - 1 -> setVariable(emptyBindingHelper.variableId, emptyBindingHelper.emptyViewModel)
            progressItemIndex.get() == -1 || position < progressItemIndex.get() -> setVariable(bindingHelper.variableId, items[position])
            progressItemIndex.get() == position -> setVariable(progressBindingHelper.variableId, progressBindingHelper.progressViewModel)
            else -> setVariable(bindingHelper.variableId, items[position - 1])
        }
        executePendingBindings()
    }

    override fun getItemViewType(position: Int): Int = when {
        progressItemIndex.get() == position -> ITEM_TYPE_PROGRESS
        position == itemCount - 1 -> ITEM_TYPE_SPACE
        else -> ITEM_TYPE_DATA
    }

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

    override fun getItemCount() = when {
        progressItemIndex.get() != -1 -> items.size + 2
        items.size == 0 -> items.size
        else -> items.size + 1
    }

    override fun setItems(newItems: List<I>): Boolean = super.setItems(newItems).also { if (it) removeProgressItem() }

    companion object {
        const val ITEM_TYPE_PROGRESS = 1
        const val ITEM_TYPE_DATA = 2
        const val ITEM_TYPE_SPACE = 3
    }
}