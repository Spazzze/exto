package com.github.spazzze.exto.databinding.abs

import androidx.recyclerview.widget.DiffUtil
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel
import java.util.*

/**
 * @author Space
 * @date 03.04.2017
 */

class DiffUtilCallback<T : IRecyclerItemViewModel> : DiffUtil.Callback() {
    private val oldItems: MutableList<T> = ArrayList()
    private val newItems: MutableList<T> = ArrayList()

    fun update(new: List<T>) = apply {
        oldItems.clear()
        oldItems.addAll(newItems)
        newItems.clear()
        newItems.addAll(new)
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id
}