package com.github.spazzze.exto.databinding.abs

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingAdapter
import com.github.spazzze.exto.databinding.interfaces.IRecyclerBindingHelper
import com.github.spazzze.exto.databinding.interfaces.IRecyclerItemViewModel
import com.github.spazzze.exto.extensions.replaceAllBy


/**
 * @author Space
 * @date 06.02.2018
 */

class ArrayDatabindingAdapter<I : IRecyclerItemViewModel>(context: Context,
                                                          override val bindingHelper: IRecyclerBindingHelper<I>,
                                                          override val items: ObservableList<I> = ObservableArrayList<I>()) :
        ArrayAdapter<I>(context, bindingHelper.layoutRes, items), IRecyclerBindingAdapter<I> {

    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
            with(DataBindingUtil.inflate<ViewDataBinding>(inflater, bindingHelper.layoutRes, parent, false)) {
                setVariable(bindingHelper.variableId, items[position])
                executePendingBindings()
                root
            }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) = getView(position, convertView, parent)

    override fun setItems(newItems: List<I>) = items.replaceAllBy(newItems)
}