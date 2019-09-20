package com.github.spazzze.exto.databinding.abs

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel

/**
 * @author Space
 * @date 24.12.2016
 */

open class RecyclerBindingAdapter<I : IRecyclerItemViewModel>(override val bindingHelper: IRecyclerBindingHelper<I>,
                                                              override var items: ObservableList<I> = ObservableArrayList()) :
        RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder>(), IRecyclerBindingAdapter<I> {

    private val diffUtilCallback = DiffUtilCallback<I>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            BindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), bindingHelper.layoutRes, parent, false))

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.binding.run {
            setVariable(bindingHelper.variableId, items[position])
            executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    override fun setItems(newItems: List<I>) = with(ObservableArrayList<I>().apply { addAll(newItems) }) {
        synchronized(items) { items = this }
        dispatchUpdates(newItems)
        true
    }

    private fun dispatchUpdates(newItems: List<I>) =
            DiffUtil.calculateDiff(diffUtilCallback.update(newItems)).dispatchUpdatesTo(this)

    class BindingHolder internal constructor(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}